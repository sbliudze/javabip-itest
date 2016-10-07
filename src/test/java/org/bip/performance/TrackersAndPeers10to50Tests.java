package org.bip.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.bip.api.BIPActor;
import org.bip.api.BIPEngine;
import org.bip.api.BIPGlue;
import org.bip.engine.factory.EngineFactory;
import org.bip.glue.GlueBuilder;
import org.bip.spec.Peer;
import org.bip.spec.Tracker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;

public class TrackersAndPeers10to50Tests {
	
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
	public void TrackerPeer10Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer15Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer20Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer25Test() {
		
		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer30Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor6 = engine.register(tracker6, "6", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);
		final BIPActor executor31 = engine.register(peer21, "31", true);
		final BIPActor executor32 = engine.register(peer22, "32", true);
		final BIPActor executor33 = engine.register(peer23, "33", true);
		final BIPActor executor34 = engine.register(peer24, "34", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer35Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor6 = engine.register(tracker6, "6", true);
		final BIPActor executor7 = engine.register(tracker7, "7", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);
		final BIPActor executor31 = engine.register(peer21, "31", true);
		final BIPActor executor32 = engine.register(peer22, "32", true);
		final BIPActor executor33 = engine.register(peer23, "33", true);
		final BIPActor executor34 = engine.register(peer24, "34", true);
		final BIPActor executor35 = engine.register(peer25, "35", true);
		final BIPActor executor36 = engine.register(peer26, "36", true);
		final BIPActor executor37 = engine.register(peer27, "37", true);
		final BIPActor executor38 = engine.register(peer28, "38", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer40Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor6 = engine.register(tracker6, "6", true);
		final BIPActor executor7 = engine.register(tracker7, "7", true);
		final BIPActor executor8 = engine.register(tracker8, "8", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);
		final BIPActor executor31 = engine.register(peer21, "31", true);
		final BIPActor executor32 = engine.register(peer22, "32", true);
		final BIPActor executor33 = engine.register(peer23, "33", true);
		final BIPActor executor34 = engine.register(peer24, "34", true);
		final BIPActor executor35 = engine.register(peer25, "35", true);
		final BIPActor executor36 = engine.register(peer26, "36", true);
		final BIPActor executor37 = engine.register(peer27, "37", true);
		final BIPActor executor38 = engine.register(peer28, "38", true);
		final BIPActor executor39 = engine.register(peer29, "39", true);
		final BIPActor executor40 = engine.register(peer30, "40", true);
		final BIPActor executor41 = engine.register(peer31, "41", true);
		final BIPActor executor42 = engine.register(peer32, "42", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TrackerPeer45Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Tracker tracker9 = new Tracker(9);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);
		Peer peer33 = new Peer(43);
		Peer peer34 = new Peer(44);
		Peer peer35 = new Peer(45);
		Peer peer36 = new Peer(46);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor6 = engine.register(tracker6, "6", true);
		final BIPActor executor7 = engine.register(tracker7, "7", true);
		final BIPActor executor8 = engine.register(tracker8, "8", true);
		final BIPActor executor9 = engine.register(tracker9, "9", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);
		final BIPActor executor31 = engine.register(peer21, "31", true);
		final BIPActor executor32 = engine.register(peer22, "32", true);
		final BIPActor executor33 = engine.register(peer23, "33", true);
		final BIPActor executor34 = engine.register(peer24, "34", true);
		final BIPActor executor35 = engine.register(peer25, "35", true);
		final BIPActor executor36 = engine.register(peer26, "36", true);
		final BIPActor executor37 = engine.register(peer27, "37", true);
		final BIPActor executor38 = engine.register(peer28, "38", true);
		final BIPActor executor39 = engine.register(peer29, "39", true);
		final BIPActor executor40 = engine.register(peer30, "40", true);
		final BIPActor executor41 = engine.register(peer31, "41", true);
		final BIPActor executor42 = engine.register(peer32, "42", true);
		final BIPActor executor43 = engine.register(peer33, "43", true);
		final BIPActor executor44 = engine.register(peer34, "44", true);
		final BIPActor executor45 = engine.register(peer35, "45", true);
		final BIPActor executor46 = engine.register(peer36, "46", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void TrackerPeer50Test() {

		BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
		BIPEngine engine = engineFactory.create("myEngine", bipGlue);
		
		Tracker tracker1 = new Tracker(1);
		Tracker tracker2 = new Tracker(2);
		Tracker tracker3 = new Tracker(3);
		Tracker tracker4 = new Tracker(4);
		Tracker tracker5 = new Tracker(5);
		Tracker tracker6 = new Tracker(6);
		Tracker tracker7 = new Tracker(7);
		Tracker tracker8 = new Tracker(8);
		Tracker tracker9 = new Tracker(9);
		Tracker tracker10 = new Tracker(10);
		Peer peer1 = new Peer(11);
		Peer peer2 = new Peer(12);
		Peer peer3 = new Peer(13);
		Peer peer4 = new Peer(14);
		Peer peer5 = new Peer(15);
		Peer peer6 = new Peer(16);
		Peer peer7 = new Peer(17);
		Peer peer8 = new Peer(18);
		Peer peer9 = new Peer(19);
		Peer peer10 = new Peer(20);
		Peer peer11 = new Peer(21);
		Peer peer12 = new Peer(22);
		Peer peer13 = new Peer(23);
		Peer peer14 = new Peer(24);
		Peer peer15 = new Peer(25);
		Peer peer16 = new Peer(26);
		Peer peer17 = new Peer(27);
		Peer peer18 = new Peer(28);
		Peer peer19 = new Peer(29);
		Peer peer20 = new Peer(30);
		Peer peer21 = new Peer(31);
		Peer peer22 = new Peer(32);
		Peer peer23 = new Peer(33);
		Peer peer24 = new Peer(34);
		Peer peer25 = new Peer(35);
		Peer peer26 = new Peer(36);
		Peer peer27 = new Peer(37);
		Peer peer28 = new Peer(38);
		Peer peer29 = new Peer(39);
		Peer peer30 = new Peer(40);
		Peer peer31 = new Peer(41);
		Peer peer32 = new Peer(42);
		Peer peer33 = new Peer(43);
		Peer peer34 = new Peer(44);
		Peer peer35 = new Peer(45);
		Peer peer36 = new Peer(46);
		Peer peer37 = new Peer(47);
		Peer peer38 = new Peer(48);
		Peer peer39 = new Peer(49);
		Peer peer40 = new Peer(50);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor2 = engine.register(tracker2, "2", true);
		final BIPActor executor3 = engine.register(tracker3, "3", true);
		final BIPActor executor4 = engine.register(tracker4, "4", true);
		final BIPActor executor5 = engine.register(tracker5, "5", true);
		final BIPActor executor6 = engine.register(tracker6, "6", true);
		final BIPActor executor7 = engine.register(tracker7, "7", true);
		final BIPActor executor8 = engine.register(tracker8, "8", true);
		final BIPActor executor9 = engine.register(tracker9, "9", true);
		final BIPActor executor10 = engine.register(tracker10, "10", true);
		final BIPActor executor11 = engine.register(peer1, "11", true);
		final BIPActor executor12 = engine.register(peer2, "12", true);
		final BIPActor executor13 = engine.register(peer3, "13", true);
		final BIPActor executor14 = engine.register(peer4, "14", true);
		final BIPActor executor15 = engine.register(peer5, "15", true);
		final BIPActor executor16 = engine.register(peer6, "16", true);
		final BIPActor executor17 = engine.register(peer7, "17", true);
		final BIPActor executor18 = engine.register(peer8, "18", true);
		final BIPActor executor19 = engine.register(peer9, "19", true);
		final BIPActor executor20 = engine.register(peer10, "20", true);
		final BIPActor executor21 = engine.register(peer11, "21", true);
		final BIPActor executor22 = engine.register(peer12, "22", true);
		final BIPActor executor23 = engine.register(peer13, "23", true);
		final BIPActor executor24 = engine.register(peer14, "24", true);
		final BIPActor executor25 = engine.register(peer15, "25", true);
		final BIPActor executor26 = engine.register(peer16, "26", true);
		final BIPActor executor27 = engine.register(peer17, "27", true);
		final BIPActor executor28 = engine.register(peer18, "28", true);
		final BIPActor executor29 = engine.register(peer19, "29", true);
		final BIPActor executor30 = engine.register(peer20, "30", true);
		final BIPActor executor31 = engine.register(peer21, "31", true);
		final BIPActor executor32 = engine.register(peer22, "32", true);
		final BIPActor executor33 = engine.register(peer23, "33", true);
		final BIPActor executor34 = engine.register(peer24, "34", true);
		final BIPActor executor35 = engine.register(peer25, "35", true);
		final BIPActor executor36 = engine.register(peer26, "36", true);
		final BIPActor executor37 = engine.register(peer27, "37", true);
		final BIPActor executor38 = engine.register(peer28, "38", true);
		final BIPActor executor39 = engine.register(peer29, "39", true);
		final BIPActor executor40 = engine.register(peer30, "40", true);
		final BIPActor executor41 = engine.register(peer31, "41", true);
		final BIPActor executor42 = engine.register(peer32, "42", true);
		final BIPActor executor43 = engine.register(peer33, "43", true);
		final BIPActor executor44 = engine.register(peer34, "44", true);
		final BIPActor executor45 = engine.register(peer35, "45", true);
		final BIPActor executor46 = engine.register(peer36, "46", true);
		final BIPActor executor47 = engine.register(peer37, "47", true);
		final BIPActor executor48 = engine.register(peer38, "48", true);
		final BIPActor executor49 = engine.register(peer39, "49", true);
		final BIPActor executor50 = engine.register(peer40, "50", true);

		engine.specifyGlue(bipGlue);
		engine.start();
		engine.execute();

		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;
	}

	@Test
	// Compute time in seconds for 1000 iterations
	public void computeTimeInSecsfor1000Iterations() throws IOException {
		int i;
		for (i = 5; i < 55; i = i + 5) {

			File file = new File("/home/mavridou/workspace/javaengineperformance/TrackersPeers/Time/100000Nodes/"
					+ "TP" + i
					+ ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count = 0;
			double sum = 0;
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (count == 1000)
					break;
				if (!line.equals("")) {
					sum += Integer.parseInt(line);
					count++;
				}
			}
			if (count == 0)
				return;
			System.out.println(i + " " + sum / 1000);
		}
	}

	@Test
	// Compute time in seconds for 1000 iterations
	public void computeAverage() throws IOException {
		int i;
		for (i = 5; i < 55; i = i + 5) {

			File file = new File("/home/mavridou/workspace/javaengineperformance/TrackersPeers/Time/100000Nodes/"
					+ "TP"
					+ i
					+ ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int count = 0;
			double sum = 0;
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.equals("")) {
					sum += Integer.parseInt(line);
					count++;
				}
			}
			if (count == 0)
				return;
			System.out.println(i + " " + sum / count + " for iterations: " + count);
		}
	}
}
