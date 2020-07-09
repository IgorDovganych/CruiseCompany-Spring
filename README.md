# CruiseCompany-Spring
Final project


Original task : 

Система Круизная Компания. У Компании имеется несколько
Кораблей. У Кораблей есть пассажиро-вместимость, маршрут, количество
посещаемых портов, длительность одного круиза, персонал. Клиент
выбирает и оплачивает круиз. Выбирает Экскурсии по прибытии в порт за
дополнительную плату. Администратор Корабля указывает бонусы для 
пассажиров, учитывая класс билета (бассейн, спорт зал, кинозал,
косметические салоны...). 

Installation
- git clone https://github.com/IgorDovganych/CruiseCompany-Spring.git

You need to have: 

- MySQL 8 
- Java 8 or higher
- Maven - to manage project's build

Configuration: 
- From the folder SQL run all scripts to create tables and fill database with the data  

Run: 

- from command line go to the project folder and execute the following command "mvn tomcat7:run"
- from browser open http://localhost:8080/FinalProjectJavaEE/
- Credentials to login : 
- Admin:  email - admin@gmail.com, password - 12345
- User: email - user@gmail.com, password - 12345

What you can do depends on your role : 
User: 
- opportunity to change the language (English, russian and ukrainin)
- you can see all available cruises and detailed information about them
- opportunity to purchase cruise if tickets are not sold out
- from personal account you will see history of purchased cruises
- you can log out

Admin, you can: 

- change the language(english, russian and ukrainin)
- see the list of all registered users , ability to change their names , password and assign them different roles 
- add bonuses to the different type of tickets , depending on their price 
- create new excursions in ports 
- create new cruise 
- activate existing cruises
- deactivate existing cruises
- activate existing excursions
- deactivate existing excursions 


Not registered user:
- you can change the language(english, russian and ukrainin)
- you can see all available cruises
- you can register
- you can login
