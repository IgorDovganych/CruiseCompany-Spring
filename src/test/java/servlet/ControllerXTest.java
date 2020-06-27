package servlet;

import dao.CruiseDao;
import dao.ExcursionDao;
import dao.TicketDao;
import dao.UserDao;
import exception.DaoException;
import model.Cruise;
import model.Ship;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ControllerXTest {

   // static final User user = new User(1L, "YA", "email@gmail.com", "wrong password", "admin") ;

    @InjectMocks
    ControllerX controller;

    @Mock
    HttpServletRequest request;

    @Mock
    UserDao userDao;

    @Mock
    CruiseDao cruiseDao;

    @Mock
    ExcursionDao excursionDao;

    @Mock
    TicketDao ticketDao;

    @Mock
    HttpSession session;


    @Captor
    ArgumentCaptor<String> attributeNameCaptor;

    @Captor
    ArgumentCaptor<String> attributeObjectCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    ArgumentCaptor<List<Cruise>> cruisesArgumentCaptor;

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

        //assert
        assertEquals(expectedReturn,actual);
    }



    @Test
    public void getAllCruises() throws DaoException {
        //setup
        List<Cruise> cruises = new ArrayList<>();
        Ship ship = new Ship(1,200,"Tanya");
        List<String> route = Arrays.asList("Oslo","Skagen");
        Cruise cruise = new Cruise(1,ship,route,new Date(),new Date(),200);
        cruises.add(cruise);
        when(cruiseDao.getAllCruises()).thenReturn(cruises);
        String expectedAttributeName = "cruises";
        String expectedReturn = "cruises";

        //act
        String actualResult = controller.getAllCruises(request);

        //assert
        verify(request).setAttribute(attributeNameCaptor.capture(), cruisesArgumentCaptor.capture());
        assertEquals(expectedAttributeName,attributeNameCaptor.getValue());
        assertEquals(cruises,cruisesArgumentCaptor.getValue());
        assertEquals(expectedReturn,actualResult);
    }

    @Test
    public void getCruiseInfoTest() throws DaoException {
        int id = 1;
        Ship ship = new Ship(1,200,"Tanya");
        List<String> route = Arrays.asList("Oslo","Skagen");
        Cruise cruise = new Cruise(1,ship,route,new Date(),new Date(),200);

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(cruiseDao.getCruiseById(id)).thenReturn(cruise);

        //act
        String actualResult = controller.getCruiseInfo(request);

        //assert
        assertEquals(cruise.getShip().getCapacity(),200);
        assertEquals("cruise_info",actualResult);
    }

    @Test
    public void getUserAccountInfoTest() throws DaoException {

        //setup
        User user = new User(1L, "YA", "email@gmail.com", "wrong password", "admin") ;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        String expectedReturn = "user_account_info";

        //act
        String actualResult = controller.getUserAccountInfo(request);

        //assert
        assertEquals(1L,user.getId());
        assertEquals(expectedReturn,actualResult);
    }

    @Test
    public void changeLanguageLinkIsNotBlank() {
        //setup
        String lang = "en";
        String parameters = "params";
        String link = "main_page";
        String expectedReturn = "redirect:main_page?params";
        when(request.getParameter("link")).thenReturn(link);
        when(request.getParameter("parameters")).thenReturn(parameters);
        when(request.getParameter("lang")).thenReturn(lang);
        when(request.getSession()).thenReturn(session);

        //act
        String actualResult = controller.changeLanguage(request);

        //assert
        assertEquals(expectedReturn,actualResult);
    }
}
