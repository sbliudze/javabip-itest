package org.bip.performance;

import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.RoutePolicy;
import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.exceptions.BIPException;
import org.bip.glue.TwoSynchronGlueBuilder;
import org.bip.spec.MonitorNoDataManyRoutes;
import org.bip.spec.SwitchableRoute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class Routes03to19Tests {

	ActorSystem system;
	EngineFactory engineFactory;
	
	@Before
	public void initialize() {

		system = ActorSystem.create("MySystem");
		engineFactory = new EngineFactory(system);

	}
	
	@After
	public void cleanup() {
		
		system.shutdown();
		
	}
	
	@Test
	public void bipDataTransferTest() throws BIPException {
		System.out.println("Switchable Routes without Data: 3+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);

		final BIPActor executor1 = engine.register(route1, "1", true);
		final BIPActor executor2 = engine.register(route2, "2", true);
		final BIPActor executor3 = engine.register(route3, "3", true);
		
		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(2);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2); 
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		
		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route5Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 5+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);

		final BIPActor executor1 = engine.register(route1, "1", true);
		final BIPActor executor2 = engine.register(route2, "2", true);
		final BIPActor executor3 = engine.register(route3, "3", true);
		final BIPActor executor4 = engine.register(route4, "4", true);
		final BIPActor executor5 = engine.register(route5, "5", true);
		
		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(3);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2); 
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4); 
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		
		

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route6Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 6+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(3);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route7Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 7+1");
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();
			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(4);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route8Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 8+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);
		final RoutePolicy routePolicy8 = createRoutePolicy(executor8);

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(4);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);
		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route9Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 9+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		final BIPActor executor9 = engine.register(route9,"9", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);
		final RoutePolicy routePolicy8 = createRoutePolicy(executor8);
		final RoutePolicy routePolicy9 = createRoutePolicy(executor9);
		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(5);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();

		engine.execute();
		try {
			Thread.sleep(800000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route10Test() throws BIPException {
		System.out.println("Switchable Routes without Data: 10+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		final BIPActor executor9 = engine.register(route9,"9", true);
		final BIPActor executor10 = engine.register(route10,"10", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);
		final RoutePolicy routePolicy8 = createRoutePolicy(executor8);
		final RoutePolicy routePolicy9 = createRoutePolicy(executor9);
		final RoutePolicy routePolicy10 = createRoutePolicy(executor10);

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(5);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route14Test() throws BIPException {

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);
		SwitchableRoute route11 = new SwitchableRoute("11", camelContext);
		SwitchableRoute route12 = new SwitchableRoute("12", camelContext);
		SwitchableRoute route13 = new SwitchableRoute("13", camelContext);
		SwitchableRoute route14 = new SwitchableRoute("14", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		final BIPActor executor9 = engine.register(route9,"9", true);
		final BIPActor executor10 = engine.register(route10,"10", true);
		final BIPActor executor11 = engine.register(route11,"11", true);
		final BIPActor executor12 = engine.register(route12,"12", true);
		final BIPActor executor13 = engine.register(route13,"13", true);
		final BIPActor executor14 = engine.register(route14,"14", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);
		final RoutePolicy routePolicy8 = createRoutePolicy(executor8);
		final RoutePolicy routePolicy9 = createRoutePolicy(executor9);
		final RoutePolicy routePolicy10 = createRoutePolicy(executor10);
		final RoutePolicy routePolicy11 = createRoutePolicy(executor11);
		final RoutePolicy routePolicy12 = createRoutePolicy(executor12);
		final RoutePolicy routePolicy13 = createRoutePolicy(executor13);
		final RoutePolicy routePolicy14 = createRoutePolicy(executor14);

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.to("file:outputfolder11");
				from("file:inputfolder12?delete=true").routeId("12").routePolicy(routePolicy12)
						.to("file:outputfolder12");
				from("file:inputfolder13?delete=true").routeId("13").routePolicy(routePolicy13)
						.to("file:outputfolder13");
				from("file:inputfolder14?delete=true").routeId("14").routePolicy(routePolicy14)
						.to("file:outputfolder14");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(7);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(20000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void route19Test() throws BIPException {

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRoute.class, "on").to(MonitorNoDataManyRoutes.class, "add");
				synchron(SwitchableRoute.class, "finished").to(MonitorNoDataManyRoutes.class, "rm");
				port(SwitchableRoute.class, "off").acceptsNothing();
				port(SwitchableRoute.class, "off").requiresNothing();

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRoute route1 = new SwitchableRoute("1", camelContext);
		SwitchableRoute route2 = new SwitchableRoute("2", camelContext);
		SwitchableRoute route3 = new SwitchableRoute("3", camelContext);
		SwitchableRoute route4 = new SwitchableRoute("4", camelContext);
		SwitchableRoute route5 = new SwitchableRoute("5", camelContext);
		SwitchableRoute route6 = new SwitchableRoute("6", camelContext);
		SwitchableRoute route7 = new SwitchableRoute("7", camelContext);
		SwitchableRoute route8 = new SwitchableRoute("8", camelContext);
		SwitchableRoute route9 = new SwitchableRoute("9", camelContext);
		SwitchableRoute route10 = new SwitchableRoute("10", camelContext);
		SwitchableRoute route11 = new SwitchableRoute("11", camelContext);
		SwitchableRoute route12 = new SwitchableRoute("12", camelContext);
		SwitchableRoute route13 = new SwitchableRoute("13", camelContext);
		SwitchableRoute route14 = new SwitchableRoute("14", camelContext);
		SwitchableRoute route15 = new SwitchableRoute("15", camelContext);
		SwitchableRoute route16 = new SwitchableRoute("16", camelContext);
		SwitchableRoute route17 = new SwitchableRoute("17", camelContext);
		SwitchableRoute route18 = new SwitchableRoute("18", camelContext);
		SwitchableRoute route19 = new SwitchableRoute("19", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		final BIPActor executor9 = engine.register(route9,"9", true);
		final BIPActor executor10 = engine.register(route10,"10", true);
		final BIPActor executor11 = engine.register(route11,"11", true);
		final BIPActor executor12 = engine.register(route12,"12", true);
		final BIPActor executor13 = engine.register(route13,"13", true);
		final BIPActor executor14 = engine.register(route14,"14", true);
		final BIPActor executor15 = engine.register(route15,"15", true);
		final BIPActor executor16 = engine.register(route16,"16", true);
		final BIPActor executor17 = engine.register(route17,"17", true);
		final BIPActor executor18 = engine.register(route18,"18", true);
		final BIPActor executor19 = engine.register(route19,"19", true);

		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2);
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4);
		final RoutePolicy routePolicy5 = createRoutePolicy(executor5);
		final RoutePolicy routePolicy6 = createRoutePolicy(executor6);
		final RoutePolicy routePolicy7 = createRoutePolicy(executor7);
		final RoutePolicy routePolicy8 = createRoutePolicy(executor8);
		final RoutePolicy routePolicy9 = createRoutePolicy(executor9);
		final RoutePolicy routePolicy10 = createRoutePolicy(executor10);
		final RoutePolicy routePolicy11 = createRoutePolicy(executor11);
		final RoutePolicy routePolicy12 = createRoutePolicy(executor12);
		final RoutePolicy routePolicy13 = createRoutePolicy(executor13);
		final RoutePolicy routePolicy14 = createRoutePolicy(executor14);
		final RoutePolicy routePolicy15 = createRoutePolicy(executor15);
		final RoutePolicy routePolicy16 = createRoutePolicy(executor16);
		final RoutePolicy routePolicy17 = createRoutePolicy(executor17);
		final RoutePolicy routePolicy18 = createRoutePolicy(executor18);
		final RoutePolicy routePolicy19 = createRoutePolicy(executor19);
		

		RouteBuilder builder1 = new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:inputfolder1?delete=true").routeId("1").routePolicy(routePolicy1).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder1");

				from("file:inputfolder2?delete=true").routeId("2").routePolicy(routePolicy2).process(new Processor() {

					public void process(Exchange exchange) throws Exception {

					}
				}).to("file:outputfolder2");

				from("file:inputfolder3?delete=true").routeId("3").routePolicy(routePolicy3).to("file:outputfolder3");
				from("file:inputfolder4?delete=true").routeId("4").routePolicy(routePolicy4).to("file:outputfolder4");
				from("file:inputfolder5?delete=true").routeId("5").routePolicy(routePolicy5).to("file:outputfolder5");
				from("file:inputfolder6?delete=true").routeId("6").routePolicy(routePolicy6).to("file:outputfolder6");
				from("file:inputfolder7?delete=true").routeId("7").routePolicy(routePolicy7).to("file:outputfolder7");
				from("file:inputfolder8?delete=true").routeId("8").routePolicy(routePolicy8).to("file:outputfolder8");
				from("file:inputfolder9?delete=true").routeId("9").routePolicy(routePolicy9).to("file:outputfolder9");
				from("file:inputfolder10?delete=true").routeId("10").routePolicy(routePolicy10)
						.to("file:outputfolder10");
				from("file:inputfolder11?delete=true").routeId("11").routePolicy(routePolicy11)
						.to("file:outputfolder11");
				from("file:inputfolder12?delete=true").routeId("12").routePolicy(routePolicy12)
						.to("file:outputfolder12");
				from("file:inputfolder13?delete=true").routeId("13").routePolicy(routePolicy13)
						.to("file:outputfolder13");
				from("file:inputfolder14?delete=true").routeId("14").routePolicy(routePolicy14)
						.to("file:outputfolder14");
				from("file:inputfolder15?delete=true").routeId("15").routePolicy(routePolicy15)
						.to("file:outputfolder15");
				from("file:inputfolder16?delete=true").routeId("16").routePolicy(routePolicy16)
						.to("file:outputfolder16");
				from("file:inputfolder17?delete=true").routeId("17").routePolicy(routePolicy17)
						.to("file:outputfolder17");
				from("file:inputfolder18?delete=true").routeId("18").routePolicy(routePolicy18)
						.to("file:outputfolder18");
				from("file:inputfolder19?delete=true").routeId("19").routePolicy(routePolicy19)
						.to("file:outputfolder19");
			}
		};
		try {
			camelContext.addRoutes(builder1);
			camelContext.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MonitorNoDataManyRoutes monitorNoDataManyRoutes = new MonitorNoDataManyRoutes(9);
		final BIPActor executorM = engine.register(monitorNoDataManyRoutes, "monitor", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	private RoutePolicy createRoutePolicy(final BIPActor executor) {

		return  new RoutePolicy() {

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

}
