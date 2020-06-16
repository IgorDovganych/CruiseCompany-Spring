package servlet;

import dao.UserDao;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import util.PasswordHash;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ControllerXTest {

    @InjectMocks
    ControllerX controller;

    @Mock
    HttpServletRequest request;

    @Mock
    UserDao userDao;

    @Mock
    HttpSession session;


    @Captor
    ArgumentCaptor<String> attributeNameCaptor;

    @Captor
    ArgumentCaptor<String> attributeObjectCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void loginPage() {
        String expected = "login";
        String actual = controller.loginPage();
        assertEquals(expected, actual);
    }

    @Test
    public void loginUserDoesNotExist() {
        //setup
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("password")).thenReturn("password");
        //act
        String actualResult = controller.login(request);
        String expected = "login";
        //assert
        assertEquals(expected, actualResult);
        verify(request).setAttribute(attributeNameCaptor.capture(), attributeObjectCaptor.capture());
        String expectedAttributeName = "error_message";
        String expectedAttributeObject = "User with provided email doesn't exist, please try again";
        assertEquals(expectedAttributeName, attributeNameCaptor.getValue());
        assertEquals(expectedAttributeObject, attributeObjectCaptor.getValue());
    }

    @Test
    public void loginUserExist() {
        //setup
        String email = "email";
        String password = "password";
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("email")).thenReturn(email);
        User user = new User(1L, "YA", email, PasswordHash.HashPasswordMD5(password), "role");
        when(userDao.findUserByEmail(email)).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        String expectedReturn = "redirect:/user_account_info";

        //act
        String actualResult = controller.login(request);

        //assert
        assertEquals(expectedReturn,actualResult);

        verify(session).setAttribute(attributeNameCaptor.capture(), userArgumentCaptor.capture());
        assertEquals(user, userArgumentCaptor.getValue());
    }

    @Test
    public void loginWrongPass(){
        //setup
        String email = "email";
        String password = "password";
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("email")).thenReturn(email);
        User user = new User(1L, "YA", email, "wrong password", "role");
        when(userDao.findUserByEmail(email)).thenReturn(user);
        //act
        String actualResult = controller.login(request);
        String expectedReturn = "login";
        String expectedAttributeErrorName = "error_message";
        String expectedAttributeErrorObject = "Wrong Password, please type again";
        String expectedAttributeEmailName = "email";
        String expectedAttributeEmailObject = "email";

        //assert
        verify(request,times(2)).setAttribute(attributeNameCaptor.capture(), attributeObjectCaptor.capture());
        List<String> capturedNames = attributeNameCaptor.getAllValues();
        List<String> capturedObjects = attributeObjectCaptor.getAllValues();

        assertEquals(expectedAttributeEmailName, capturedNames.get(0));
        assertEquals(expectedAttributeEmailObject, capturedObjects.get(0));
        assertEquals(expectedAttributeErrorName, capturedNames.get(1));
        assertEquals(expectedAttributeErrorObject, capturedObjects.get(1));

        assertEquals(expectedReturn,actualResult);
    }

    @Test
    public void logout() {
        //setup
        when(request.getSession()).thenReturn(session);
        //act
        String actualResult = controller.logout(request);
        String expectedReturn = "redirect:/";
        //assert
        assertEquals(expectedReturn,actualResult);

    }

    @Test
    public void registrationPage() {
        //setup
        String expectedReturn = "registration_page";
        //act
        String actual = controller.registrationPage(request);
        assertEquals(expectedReturn,actual);


    }
}
