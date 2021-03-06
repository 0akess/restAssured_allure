package register.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import site.stellarburgers.nomoreparties.data.GetDataUser;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.requests.user.DeleteUser;
import site.stellarburgers.nomoreparties.requests.user.PostRegister;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Позитивный набор тестов на ручку PostRegisterUser")
public class PositiveRegisterUserTest {

    private static String token;

    @Test
    @DisplayName("Позитивная проверка регистрации")
    public void registerUser_Success() {

        GetDataUser list = new GetDataUser();
        PostRegister registerUser = new PostRegister();
        ValidatableResponse response = registerUser.registerUser
                        (new User(
                                list.dataForRegister().get(0), //почта
                                list.dataForRegister().get(1), // пароль
                                list.dataForRegister().get(2))) // логин
                .statusCode(HttpStatus.SC_OK);

        token = response.extract().path("accessToken");

        assertThat(
                response.extract().path("success"),
                equalTo(true));
    }

    @After
    @DisplayName("Удаляем тестового пользователя")
    public void endTests() {
        new DeleteUser().deleteUser(token)
                .statusCode(HttpStatus.SC_ACCEPTED);
    }
}