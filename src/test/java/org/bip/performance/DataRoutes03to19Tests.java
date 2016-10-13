package org.bip.performance;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import org.bip.spec.MemoryMonitor;
import org.bip.spec.SwitchableRouteDataTransfers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class DataRoutes03to19Tests {

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
	public void dataRouteTestCreator() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		int start = 1;
		int end = 100;
		
		//SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		for (int i = start; i < end; i++) {
			String s1 = "SwitchableRouteDataTransfers route";
			String s2 = " = new SwitchableRouteDataTransfers(\"";
			writer.println(s1 + i + s2 + i + "\", camelContext);");
		}
		writer.println("");
		//final BIPActor executor5 = engine.register(route5, "5", true);
		for (int i=start; i<end; i++) {
			String s1 = "final BIPActor executor"; 
			String s2=" = engine.register(route";
			writer.println(s1+i+s2+i+",\""+i+"\", true);");
		}
		writer.println("");
		//final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		for (int i=start; i<end; i++) {
			String s1 = "final RoutePolicy routePolicy"; 
			String s2=" = createRoutePolicy(executor";
			writer.println(s1+i+s2+i+");");
		}

		
		
		writer.close();
	}
	
	@Test
	public void dataRoute4Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 5+1");
		
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		
	
		
		final BIPActor executor1 = engine.register(route1, "1", true);
		final BIPActor executor2 = engine.register(route2, "2", true);
		final BIPActor executor3 = engine.register(route3, "3", true);
		final BIPActor executor4 = engine.register(route4, "4", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(500);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);
		
		final RoutePolicy routePolicy1 = createRoutePolicy(executor1);
		final RoutePolicy routePolicy2 = createRoutePolicy(executor2); 
		final RoutePolicy routePolicy3 = createRoutePolicy(executor3);
		final RoutePolicy routePolicy4 = createRoutePolicy(executor4); 

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
	public void dataRoute5Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 5+1");
		
		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		
	
		
		final BIPActor executor1 = engine.register(route1, "1", true);
		final BIPActor executor2 = engine.register(route2, "2", true);
		final BIPActor executor3 = engine.register(route3, "3", true);
		final BIPActor executor4 = engine.register(route4, "4", true);
		final BIPActor executor5 = engine.register(route5, "5", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(500);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);
		
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
	public void dataRoute6Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 6+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(600);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);
		
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
	public void dataRoute7Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 7+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");
			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(700);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);
		
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
	public void dataRoute8Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 8+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers("8", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(800);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);

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
	public void dataRoute9Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 9+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {

				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers("8", camelContext);
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers("9", camelContext);

		final BIPActor executor1 = engine.register(route1,"1", true);
		final BIPActor executor2 = engine.register(route2,"2", true);
		final BIPActor executor3 = engine.register(route3,"3", true);
		final BIPActor executor4 = engine.register(route4,"4", true);
		final BIPActor executor5 = engine.register(route5,"5", true);
		final BIPActor executor6 = engine.register(route6,"6", true);
		final BIPActor executor7 = engine.register(route7,"7", true);
		final BIPActor executor8 = engine.register(route8,"8", true);
		final BIPActor executor9 = engine.register(route9,"9", true);
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(900);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);

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
	public void dataRoute10Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 10+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers("8", camelContext);
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers("9", camelContext);
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers("10", camelContext);

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
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1000);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);

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
	public void dataRoute14Test() throws BIPException {

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers("8", camelContext);
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers("9", camelContext);
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers("10", camelContext);
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers("11", camelContext);
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers("12", camelContext);
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers("13", camelContext);
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers("14", camelContext);

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
		
		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(800);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);

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


		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();
		try {
			Thread.sleep(800000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Route 1 has not made any transitions", route1.noOfEnforcedTransitions > 0);
		assertTrue("Route 2 has not made any transitions", route2.noOfEnforcedTransitions > 0);
		assertTrue("Route 3 has not made any transitions", route3.noOfEnforcedTransitions > 0);
	}

	@Test
	public void dataRoute19Test() throws BIPException {
		System.out.println("Switchable Routes with Data: 19+1");

		BIPGlue bipGlue = new TwoSynchronGlueBuilder() {
			@Override
			public void configure() {
				synchron(SwitchableRouteDataTransfers.class, "on").to(MemoryMonitor.class, "add");
				synchron(SwitchableRouteDataTransfers.class, "finished").to(MemoryMonitor.class, "rm");
				port(SwitchableRouteDataTransfers.class, "off").acceptsNothing();
				port(SwitchableRouteDataTransfers.class, "off").requiresNothing();
				data(SwitchableRouteDataTransfers.class, "deltaMemoryOnTransition").to(MemoryMonitor.class,
						"memoryUsage");

			}

		}.build();
		
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		CamelContext camelContext = new DefaultCamelContext();
		camelContext.setAutoStartup(false);

		SwitchableRouteDataTransfers route1 = new SwitchableRouteDataTransfers("1", camelContext);
		SwitchableRouteDataTransfers route2 = new SwitchableRouteDataTransfers("2", camelContext);
		SwitchableRouteDataTransfers route3 = new SwitchableRouteDataTransfers("3", camelContext);
		SwitchableRouteDataTransfers route4 = new SwitchableRouteDataTransfers("4", camelContext);
		SwitchableRouteDataTransfers route5 = new SwitchableRouteDataTransfers("5", camelContext);
		SwitchableRouteDataTransfers route6 = new SwitchableRouteDataTransfers("6", camelContext);
		SwitchableRouteDataTransfers route7 = new SwitchableRouteDataTransfers("7", camelContext);
		SwitchableRouteDataTransfers route8 = new SwitchableRouteDataTransfers("8", camelContext);
		SwitchableRouteDataTransfers route9 = new SwitchableRouteDataTransfers("9", camelContext);
		SwitchableRouteDataTransfers route10 = new SwitchableRouteDataTransfers("10", camelContext);
		SwitchableRouteDataTransfers route11 = new SwitchableRouteDataTransfers("11", camelContext);
		SwitchableRouteDataTransfers route12 = new SwitchableRouteDataTransfers("12", camelContext);
		SwitchableRouteDataTransfers route13 = new SwitchableRouteDataTransfers("13", camelContext);
		SwitchableRouteDataTransfers route14 = new SwitchableRouteDataTransfers("14", camelContext);
		SwitchableRouteDataTransfers route15 = new SwitchableRouteDataTransfers("15", camelContext);
		SwitchableRouteDataTransfers route16 = new SwitchableRouteDataTransfers("16", camelContext);
		SwitchableRouteDataTransfers route17 = new SwitchableRouteDataTransfers("17", camelContext);
		SwitchableRouteDataTransfers route18 = new SwitchableRouteDataTransfers("18", camelContext);
		SwitchableRouteDataTransfers route19 = new SwitchableRouteDataTransfers("19", camelContext);

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

		MemoryMonitor routeOnOffMonitor = new MemoryMonitor(1000);
		final BIPActor executorM = engine.register(routeOnOffMonitor, "monitor", true);
		
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
