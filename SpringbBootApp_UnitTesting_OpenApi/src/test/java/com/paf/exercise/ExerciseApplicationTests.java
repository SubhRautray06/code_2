package com.paf.exercise;

import com.paf.exercise.dto.PlayerDto;
import com.paf.exercise.dto.TournamentDto;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.repository.TestH2PlayerRepository;
import com.paf.exercise.repository.TestH2TournamentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT) //run with random port
class ExerciseApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl= "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private TestH2TournamentRepository testH2TournamentRepository;
	@Autowired
	private TestH2PlayerRepository testH2PlayerRepository;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}
	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1");
	}

	@Test
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testAddTournament(){
		TournamentDto tournamentDto = new TournamentDto();
		tournamentDto.setTournamentName("Hockey");
		tournamentDto.setRewardAmount(1000);
		TournamentDto response = restTemplate.postForObject(baseUrl+"/tournaments",tournamentDto, TournamentDto.class);
		assertEquals("Hockey", response.getTournamentName());
		assertEquals(1, testH2TournamentRepository.findAll().size());
	}

	@Test
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testGetTournaments(){

		List<TournamentDto> response = restTemplate.getForObject(baseUrl+ "/tournaments", List.class);
		assertEquals(1, response.size());
		assertEquals(1, testH2TournamentRepository.findAll().size());
	}


	@Test
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testFindTournamentById() {
		TournamentDto response = restTemplate.getForObject(baseUrl+ "/tournaments" + "/{id}", TournamentDto.class, 1);
		System.out.println(response);
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(1, response.getId()),
				() -> assertEquals("HOCKEY", response.getTournamentName())
		);

	}

	@Test
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (2,'CRICKET', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='CRICKET'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testUpdateTournament(){
		TournamentDto tournamentDto = new TournamentDto();
		tournamentDto.setTournamentName("CRICKET");
		tournamentDto.setRewardAmount(10000);
		restTemplate.put(baseUrl+"/tournaments/{tournamentId}", tournamentDto, 2L);
		Tournament response = testH2TournamentRepository.findByTournamentId(2L).get();
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(10000, response.getRewardAmount())
		);
	}

	@Test
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteTournament(){
		int recordCount=testH2TournamentRepository.findAll().size();
		assertEquals(1, recordCount);
		restTemplate.delete(baseUrl+"/tournaments/{id}", 1);
		assertEquals(0, testH2TournamentRepository.findAll().size());

	}

	@Test
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO TBL_PLAYER (player_id,player_name,tournament_id) VALUES (1,'Alex', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_PLAYER WHERE player_id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testGetAllPlayers(){

		List<PlayerDto> response = restTemplate.getForObject(baseUrl+ "/players" + "/{tournamentId}", List.class,1);
		assertEquals(1, response.size());
		assertEquals(1, testH2TournamentRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_PLAYER WHERE player_id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testAddPlayer(){
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayerName("Frodo");
		playerDto.setTournamentId(1L);
		PlayerDto response = restTemplate.postForObject(baseUrl+"/players",playerDto, PlayerDto.class);
		assertEquals("Frodo", response.getPlayerName());
		assertEquals(1, testH2TournamentRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO TBL_TOURNAMENT (tournament_id,tournament_name, reward_amount) VALUES (1,'HOCKEY', 1000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO TBL_PLAYER (player_id,player_name,tournament_id) VALUES (1,'Samwise', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_PLAYER WHERE player_id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM TBL_TOURNAMENT WHERE tournament_name='HOCKEY'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testDeletePlayerByTournamentId(){
		int recordCount=testH2PlayerRepository.findAll().size();
		assertEquals(1, recordCount);
		restTemplate.delete(baseUrl+"/players/{tournamentId}/{playerId}", 1,1);
		assertEquals(0, testH2PlayerRepository.findAll().size());

	}


}
