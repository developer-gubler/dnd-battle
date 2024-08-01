package com.schadraq.dnd_battle.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.schadraq.dnd_battle.BattleController;
import com.schadraq.dnd_battle.DndBattleApplication;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * NOTE: It is important to update the application-test.properties to leverage
 * 		 the H2 settings and comment out thePostgreSQL  settings.
 * 
 * NOTE: This test class starts the web server and processes the request just
 * like a normal request would. Tomcat can be seen to be started in the console
 * log.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DndBattleApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)	//  NOTE: tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance) and use that to start a Spring application context
@ActiveProfiles("test")
@Slf4j
public class AlignmentTest extends PersistenceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AlignmentRepository alignmentRepository;

    @Autowired
    private BattleController controller;

	@Test
	void contextLoads() throws Exception {

		///////////////////////////////////////////////////////////////////////
		// NOTE: Sanity check - fail if the application context cannot start
		assertThat(controller).isNotNull();
	}

	@Test
	void testAlignmentWebLayer() throws Exception {
//		log.info(this.restTemplate.getForObject("http://localhost:" + port + "/dnd-battle/alignments",String.class));
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dnd-battle/alignments",
				String.class)).contains("unaligned");
	}

    @Test
    public void testAlignmentModelLayer() {

        ///////////////////////////////////////////////////////////////////////
        // NOTE: Test to see if we can retrieve an alignment
    	Alignment alignment = readRecord(alignmentRepository, 1L, (found) -> {});
    	if (alignment != null) {
    		log.info("alignment name: " + alignment.getName());
    	}

        ///////////////////////////////////////////////////////////////////////
        // NOTE: Test to see if we can insert a new alignment
    	Alignment a = createRecord(false, alignmentRepository, new Alignment(ALIGNMENT_NAME, ALIGNMENT_ABBR, ALIGNMENT_DESCRIPTION));
    	if (a != null) {
    		readRecord(alignmentRepository, a, (found) -> {assertEquals(a.getName(), found.getName());assertEquals(a.getAbbr(), found.getAbbr());} );
    	}

        ///////////////////////////////////////////////////////////////////////
        // NOTE: Test to see if we can insert an alignment with the same abbr
    	Alignment b = createRecord(true, alignmentRepository, new Alignment(ALIGNMENT_NAME + "1", ALIGNMENT_ABBR, ALIGNMENT_DESCRIPTION + "1"));
    	if (b != null) {
    		readRecord(alignmentRepository, b.getId(), (found) -> {assertEquals(b.getName(), found.getName());assertEquals(b.getAbbr(), found.getAbbr());} );
    	}

        ///////////////////////////////////////////////////////////////////////
        // NOTE: Test to see if we can insert an alignment with the same name
    	Alignment c = createRecord(true, alignmentRepository, new Alignment(ALIGNMENT_NAME, ALIGNMENT_ABBR + "1", ALIGNMENT_DESCRIPTION + "1"));
    	if (c != null) {
    		readRecord(alignmentRepository, c.getId(), (found) -> {assertEquals(c.getName(), found.getName());assertEquals(c.getAbbr(), found.getAbbr());} );
    	}

        ///////////////////////////////////////////////////////////////////////
        // NOTE: Test to see if we can insert an alignment with the same desc
    	Alignment d = createRecord(true, alignmentRepository, new Alignment(ALIGNMENT_NAME + "1", ALIGNMENT_ABBR + "1", ALIGNMENT_DESCRIPTION));
    	if (d != null) {
    		readRecord(alignmentRepository, d.getId(), (found) -> {assertEquals(d.getName(), found.getName());assertEquals(d.getAbbr(), found.getAbbr());} );
    	}
    }
}
