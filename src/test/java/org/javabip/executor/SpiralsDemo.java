package org.javabip.executor;

import akka.actor.ActorSystem;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.exceptions.BIPException;
import org.javabip.glue.TwoSynchronGlueBuilder;
import org.javabip.glue.ManySynchronGlueBuilder;
import org.javabip.spec.*;

public class SpiralsDemo {
    private ActorSystem system;
    private EngineFactory engineFactory;

    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }

    private void cleanup() {
        system.shutdown();
    }

    private RoutePolicy createRoutePolicy(final BIPActor executor) {

        return new RoutePolicy() {

            public void onInit(Route route) {
            }

            public void onExchangeDone(Route route, Exchange exchange) {

                executor.inform("end");
            }

            public void onExchangeBegin(Route route, Exchange exchange) {
            }

            @Override
            public void onRemove(Route arg0) {
            }

            @Override
            public void onResume(Route arg0) {
            }

            @Override
            public void onStart(Route arg0) {
            }

            @Override
            public void onStop(Route arg0) {
            }

            @Override
            public void onSuspend(Route arg0) {
            }
        };

    }

    private void Naked () throws BIPException {
        CamelContext camelContext = new DefaultCamelContext();

        RouteBuilder builder = new RouteBuilder() {

            @Override
            public void configure() {
                from("file:inputfolder1?delete=true").routeId("1").to("file:inputfolder2");

                from("file:inputfolder2?delete=true").routeId("2").to("file:inputfolder3");

                from("file:inputfolder3?delete=true").routeId("3").to("file:inputfolder4");

                from("file:inputfolder4?delete=true").routeId("4").to("file:inputfolder1");
            }
        };

        try {
            camelContext.addRoutes(builder);
            camelContext.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		try {
            camelContext.suspendRoute("1");
            camelContext.suspendRoute("2");
            camelContext.suspendRoute("3");
            camelContext.suspendRoute("4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Coordinated (Object monitor, BIPGlue bipGlue) {

        // Create a BIP engine
        final BIPEngine engine = engineFactory.create("myEngine", bipGlue);

        // If there is a monitor, register it with the BIP engine
        final BIPActor executorM = (monitor == null) ? null :
                engine.register(monitor, "monitor", true);

        // Prepare four BIP specifications for switchable Camel routes
        SwitchableRoute route1 = new SwitchableRoute("1");
        SwitchableRoute route2 = new SwitchableRoute("2");
        SwitchableRoute route3 = new SwitchableRoute("3");
        SwitchableRoute route4 = new SwitchableRoute("4");

        CamelContext camelContext = new DefaultCamelContext();
        route1.setCamelContext(camelContext);
        route2.setCamelContext(camelContext);
        route3.setCamelContext(camelContext);
        route4.setCamelContext(camelContext);

        // Register the four specification components for the routes with the BIP engine
        final BIPActor executor1 = engine.register(route1, "1", true);
        final BIPActor executor2 = engine.register(route2, "2", true);
        final BIPActor executor3 = engine.register(route3, "3", true);
        final BIPActor executor4 = engine.register(route4, "4", true);

        // Prepare the Camel routes

        // The (same) policy configures each route to notify its corresponding executor
        // when it has finished transferring the current chunk of data
        final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
        final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
        final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
        final RoutePolicy routePolicy4 = createRoutePolicy(executor4);

        // Configure the routes
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).to("file:inputfolder2");

                from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).to("file:inputfolder3");

                from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:inputfolder4");

                from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:inputfolder1");
            }
        };
        camelContext.setAutoStartup(false);

        try {
            camelContext.addRoutes(builder);
            camelContext.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the BIP engine
        engine.specifyGlue(bipGlue);
        engine.start();
        engine.execute();

        // Let the system run for some seconds to allow some transitions to be fired
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If there is a monitor, tell it to forbid turning routes on
        //      and wait for all of them to be suspended
        // Else suspend them manually
        if (executorM != null) {
            executorM.inform("switch");

            while (!(executorM.getState().equals("0"))) {
                try {
                    Thread.sleep (100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                camelContext.suspendRoute("1");
                camelContext.suspendRoute("2");
                camelContext.suspendRoute("3");
                camelContext.suspendRoute("4");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Stop the BIP engine
        engine.stop();
        engineFactory.destroy(engine);
    }

    private void CoordinatedWithIds (Object monitor, BIPGlue bipGlue) {

        // Create a BIP engine
        final BIPEngine engine = engineFactory.create("myEngine", bipGlue);

        // If there is a monitor, register it with the BIP engine
        final BIPActor executorM = (monitor == null) ? null :
                engine.register(monitor, "monitor", true);

        // THE ONLY DIFFERENCE WITH Coordinated () IS HERE
        // Prepare four BIP specifications for switchable Camel routes
        SwitchableRoute1 route1 = new SwitchableRoute1("1");
        SwitchableRoute2 route2 = new SwitchableRoute2("2");
        SwitchableRoute3 route3 = new SwitchableRoute3("3");
        SwitchableRoute4 route4 = new SwitchableRoute4("4");
        // END OF DIFFERENCE

        CamelContext camelContext = new DefaultCamelContext();
        route1.setCamelContext(camelContext);
        route2.setCamelContext(camelContext);
        route3.setCamelContext(camelContext);
        route4.setCamelContext(camelContext);

        // Register the four specification components for the routes with the BIP engine
        final BIPActor executor1 = engine.register(route1, "1", true);
        final BIPActor executor2 = engine.register(route2, "2", true);
        final BIPActor executor3 = engine.register(route3, "3", true);
        final BIPActor executor4 = engine.register(route4, "4", true);

        // Prepare the Camel routes

        // The (same) policy configures each route to notify its corresponding executor
        // when it has finished transferring the current chunk of data
        final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
        final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
        final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
        final RoutePolicy routePolicy4 = createRoutePolicy(executor4);

        // Configure the routes
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).to("file:inputfolder2");

                from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).to("file:inputfolder3");

                from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:inputfolder4");

                from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:inputfolder1");
            }
        };
        camelContext.setAutoStartup(false);

        try {
            camelContext.addRoutes(builder);
            camelContext.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the BIP engine
        engine.specifyGlue(bipGlue);
        engine.start();
        engine.execute();

        // Let the system run for 20 seconds
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If there is a monitor, tell it to forbid turning routes on
        //      and wait for all of them to be suspended
        // Else suspend them manually
        if (executorM != null) {
            executorM.inform("switch");

            while (!(executorM.getState().equals("0"))) {
                try {
                    Thread.sleep (100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                camelContext.suspendRoute("1");
                camelContext.suspendRoute("2");
                camelContext.suspendRoute("3");
                camelContext.suspendRoute("4");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Stop the BIP engine
        engine.stop();
        engineFactory.destroy(engine);
    }

    public static void main (String[] args) {
        SpiralsDemo demo = new SpiralsDemo();

        demo.initialize();

        /* Run only the Camel routes, without even a BIP engine */
//        demo.Naked();

        /* Exactly the same thing, but with BIP specifications and a BIP engine */
//        demo.Coordinated(null, new TwoSynchronGlueBuilder() {
//            @Override
//            public void configure() {
//                port(SwitchableRoute.class, "on").acceptsNothing();
//                port(SwitchableRoute.class, "on").requiresNothing();
//
//                port(SwitchableRoute.class, "finished").acceptsNothing();
//                port(SwitchableRoute.class, "finished").requiresNothing();
//
//                port(SwitchableRoute.class, "off").acceptsNothing();
//                port(SwitchableRoute.class, "off").requiresNothing();
//            }
//        }.build());

        /* Add a simple monitor with two states: 0 and 1
           In state 0, no routes are running
           In state 1, an unknown non-zero number of routes are running
         */
//        demo.Coordinated(new RouteSimpleMonitor(), new TwoSynchronGlueBuilder() {
//            @Override
//            public void configure() {
//                synchron(SwitchableRoute.class, "on").to(RouteSimpleMonitor.class, "add");
//                synchron(SwitchableRoute.class, "finished").to(RouteSimpleMonitor.class, "rm");
//                synchron(SwitchableRoute.class, "finished").to(RouteSimpleMonitor.class, "rmlast");
//
//                port(SwitchableRoute.class, "off").acceptsNothing();
//                port(SwitchableRoute.class, "off").requiresNothing();
//
//            }
//        }.build());

        /* Add a monitor with three states -- 0, 1 and 2 -- corresponding to the number of
           running routes
         */
//        demo.Coordinated(new RouteOnOffMonitor(2), new TwoSynchronGlueBuilder() {
//            @Override
//            public void configure() {
//                synchron(SwitchableRoute.class, "on").to(RouteOnOffMonitor.class, "add");
//                synchron(SwitchableRoute.class, "finished").to(RouteOnOffMonitor.class, "rm");
//
//                port(SwitchableRoute.class, "off").acceptsNothing();
//                port(SwitchableRoute.class, "off").requiresNothing();
//
//            }
//        }.build());

        /* Add a monitor (similar to the simple monitor above, but only having two actions:
           0 -- add --> 1   (when work is authorised)
           1 -- rm --> 0    (when work is not authorised)
           The routes coordinate starting and stopping directly among themselves to ensure
           that at most one is running.
         */
//        demo.Coordinated(new RouteStarter(), new TwoSynchronGlueBuilder(){
//            @Override
//            public void configure() {
//                synchron(SwitchableRoute.class, "on").to(SwitchableRoute.class, "finished");
//
//                synchron(SwitchableRoute.class, "on").to(RouteStarter.class, "add");
//                synchron(SwitchableRoute.class, "finished").to(RouteStarter.class, "rm");
//
//                port(SwitchableRoute.class, "off").acceptsNothing();
//                port(SwitchableRoute.class, "off").requiresNothing();
//            }
//        }.build());

        /* Similar to the previous one, but the routes now have identities w.r.t. the BIP engine,
           so the order of execution can be fixed by the glue. Here, they work in cycles:
           (1 -> 2 -> 3 -> 4)*
           only the 1st can start, only the 4th can finish
         */
        demo.CoordinatedWithIds(new RouteStarter(), new TwoSynchronGlueBuilder(){
            @Override
            public void configure() {
                synchron(SwitchableRoute1.class, "on").to(SwitchableRoute4.class, "finished");
                synchron(SwitchableRoute2.class, "on").to(SwitchableRoute1.class, "finished");
                synchron(SwitchableRoute3.class, "on").to(SwitchableRoute2.class, "finished");
                synchron(SwitchableRoute4.class, "on").to(SwitchableRoute3.class, "finished");

                synchron(SwitchableRoute1.class, "on").to(RouteStarter.class, "add");
                synchron(SwitchableRoute4.class, "finished").to(RouteStarter.class, "rm");

                port(SwitchableRoute1.class, "off").acceptsNothing();
                port(SwitchableRoute1.class, "off").requiresNothing();
                port(SwitchableRoute2.class, "off").acceptsNothing();
                port(SwitchableRoute2.class, "off").requiresNothing();
                port(SwitchableRoute3.class, "off").acceptsNothing();
                port(SwitchableRoute3.class, "off").requiresNothing();
                port(SwitchableRoute4.class, "off").acceptsNothing();
                port(SwitchableRoute4.class, "off").requiresNothing();
            }
        }.build());

        /* Similar to the previous one, but the routes operate by pairs:
           (1 3 -> 2 4)*
           only 1-3 can start, any two can finish
         */
//        demo.CoordinatedWithIds(new RouteStarter(), new ManySynchronGlueBuilder(){
//            @Override
//            public void configure() {
//                synchrons(SwitchableRoute1.class, "on",
//                        SwitchableRoute2.class, "finished",
//                        SwitchableRoute3.class, "on",
//                        SwitchableRoute4.class, "finished"
//                );
//
//                synchrons(SwitchableRoute2.class, "on",
//                        SwitchableRoute3.class, "finished",
//                        SwitchableRoute4.class, "on",
//                        SwitchableRoute1.class, "finished"
//                );
//
//                synchrons(SwitchableRoute1.class, "on",
//                        SwitchableRoute3.class, "on",
//                        RouteStarter.class, "add"
//                        );
//
//                synchrons(SwitchableRoute1.class, "finished",
//                        SwitchableRoute3.class, "finished",
//                        RouteStarter.class, "rm");
//                synchrons(SwitchableRoute2.class, "finished",
//                        SwitchableRoute4.class, "finished",
//                        RouteStarter.class, "rm");
//
//                port(SwitchableRoute1.class, "off").acceptsNothing();
//                port(SwitchableRoute1.class, "off").requiresNothing();
//                port(SwitchableRoute2.class, "off").acceptsNothing();
//                port(SwitchableRoute2.class, "off").requiresNothing();
//                port(SwitchableRoute3.class, "off").acceptsNothing();
//                port(SwitchableRoute3.class, "off").requiresNothing();
//                port(SwitchableRoute4.class, "off").acceptsNothing();
//                port(SwitchableRoute4.class, "off").requiresNothing();
//            }
//        }.build());

//        demo.Coordinated(new RouteOnOffMonitorWithData(), new ManySynchronGlueBuilder(){
//            @Override
//            public void configure(){
//                synchrons(SwitchableRoute.class, "on", RouteOnOffMonitorWithData.class, "add");
//                synchrons(SwitchableRoute.class, "finished", RouteOnOffMonitorWithData.class, "rm");
//
//                port(SwitchableRoute.class, "off").acceptsNothing();
//                port(SwitchableRoute.class, "off").requiresNothing();
//
//                data(SwitchableRoute.class, "id").to(RouteOnOffMonitorWithData.class, "routeId");
//            }
//
//        }.build());

        demo.cleanup();
    }

}
