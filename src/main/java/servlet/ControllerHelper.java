package servlet;

import commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {

    private static HashMap<String, Command> commands = new HashMap<String, Command>();
    private static ControllerHelper instance = new ControllerHelper();

    private ControllerHelper() {
        commands.put("/login", new LoginCommand());
        commands.put("/cruise_info", new CruiseInfoCommand());
        commands.put("/purchase_ticket", new PurchaseTicketCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/user_account_info", new UserAccountInfoCommand());
        commands.put("/cruises", new GetAllCruisesCommand());
        commands.put("/admin/users", new GetAllUsersCommand());
        commands.put("/login_page", new LoginPageCommand());
        commands.put("/registration_page", new RegistrationPageCommand());
        commands.put("/admin/update_user_page", new UpdateUserPageCommand());
        commands.put("/admin/update_user", new UpdateUserCommand());
        commands.put("/change_language", new ChangeLanguageCommand());
        commands.put("/admin/tickets", new TicketsPageCommand());
        commands.put("/admin/delete_bonus",new DeleteBonusCommand());
        commands.put("/admin/add_bonus",new AddBonusCommand());
        commands.put("/admin/excursions", new ExcursionsPageCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/admin/deactivate_excursion", new DeactivateExcursionCommand());
        commands.put("/admin/activate_excursion", new ActivateExcursionCommand());
        commands.put("/admin/create_cruise_page" , new CreateCruisePageCommand());
        commands.put("/admin/create_cruise" , new CreateCruiseCommand());
    }

    public static ControllerHelper getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        Command command = commands.get(request.getServletPath());			// getting command by servlet path
        if(command == null){
            command = new CommandDoesntExist();
        }
        return command;
    }
}
