import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.example.mobi7.Mobi7Application;
import com.example.mobi7.core.model.Poi;
import com.example.mobi7.usecase.dto.position.PositionRequest;
import com.example.mobi7.usecase.dto.position.PositionResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = Mobi7Application.class)
public class Mobi7ApplicationTests {

  private static final String BASE_URL = "http://localhost:8080";

  @BeforeEach
  public void setUp() {
    RestAssured.baseURI = BASE_URL;
  }

  @Test
  public void testCreatePOI() throws Exception {
    String actualResponse =
        given()
            .contentType(ContentType.JSON)
            .body(Arrays.asList(createTestPoi()))
            .when()
            .post("pois/create")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .asString();

    Poi expectedPoi = createTestPoi();
    JSONArray jsonArray = new JSONArray(actualResponse);
    JSONObject jsonResponse = jsonArray.getJSONObject(0);
    assertThat(jsonResponse.getString("nome"), equalTo(expectedPoi.getNome()));
    assertThat(jsonResponse.getDouble("latitude"), equalTo(expectedPoi.getLatitude()));
    assertThat(jsonResponse.getDouble("longitude"), equalTo(expectedPoi.getLongitude()));
    assertThat(jsonResponse.getDouble("raio"), equalTo(expectedPoi.getRaio()));
  }

  @Test
  public void testCreatePosition() throws Exception {
    PositionResponse positionResponse = createPositionResponse();
    String actualResponse =
        given()
            .contentType(ContentType.JSON)
            .body(
                Arrays.asList(
                    createPositionRequest(
                        "Wed Dec 12 2018 00:04:03 GMT-0200 (Hora oficial do Brasil)")))
            .when()
            .post("positions/create")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .asString();
    JSONArray jsonArray = new JSONArray(actualResponse);
    JSONObject jsonResponse = jsonArray.getJSONObject(0);
    assertThat(
        LocalDateTime.parse(jsonResponse.getString("data_posicao")),
        equalTo(positionResponse.getData_posicao()));
    assertThat(jsonResponse.getString("placa"), equalTo(positionResponse.getPlaca()));
    assertThat(jsonResponse.getDouble("latitude"), equalTo(positionResponse.getLatitude()));
    assertThat(jsonResponse.getDouble("longitude"), equalTo(positionResponse.getLongitude()));
    assertThat(jsonResponse.getBoolean("ignicao"), equalTo(positionResponse.isIgnicao()));
  }

  @Test
  public void testGetVehiclesInPoi() throws Exception {
    given()
        .contentType(ContentType.JSON)
        .body(Arrays.asList(createTestPoi()))
        .post("pois/create")
        .then()
        .statusCode(HttpStatus.CREATED.value());

    given()
        .contentType(ContentType.JSON)
        .body(
            Arrays.asList(
                createPositionRequest("Wed Dec 12 2018 00:04:03 GMT-0200 (Hora oficial do Brasil)"),
                createPositionRequest(
                    "Wed Dec 12 2018 05:51:44 GMT-0200 (Hora oficial do Brasil)")))
        .post("positions/create")
        .then()
        .statusCode(HttpStatus.CREATED.value());

    String actualResponse =
        given()
            .contentType(ContentType.JSON)
            .queryParam("poiNome", "PONTO1")
            .queryParam("placas", "TESTE1")
            .when()
            .get("positions/vehicle-in-poi")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .asString();

    JSONObject jsonResponse = new JSONObject(actualResponse);

    assertThat(jsonResponse.has("TESTE1"), equalTo(true));
    assertThat(jsonResponse.getString("TESTE1"), equalTo("PT5H47M41S"));
  }

  private Poi createTestPoi() {
    Poi poi = new Poi();
    poi.setNome("PONTO1");
    poi.setLatitude(-23.550520);
    poi.setLongitude(-46.633308);
    poi.setRaio(500.0);
    return poi;
  }

  private PositionRequest createPositionRequest(String data_posicao) {
    PositionRequest position = new PositionRequest();
    position.setPlaca("TESTE1");
    position.setData_posicao(data_posicao);
    position.setVelocidade(60.5F);
    position.setLongitude(123.456);
    position.setLatitude(-123.456);
    position.setIgnicao(true);
    return position;
  }

  private PositionResponse createPositionResponse() {
    PositionResponse position = new PositionResponse();
    position.setPlaca("TESTE1");
    position.setData_posicao(LocalDateTime.parse("2018-12-12T00:04:03"));
    position.setVelocidade(60.5F);
    position.setLongitude(123.456);
    position.setLatitude(-123.456);
    position.setIgnicao(true);
    return position;
  }
}
