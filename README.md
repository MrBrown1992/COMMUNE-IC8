# COMMUNE-IC8
## Die WG-Webapp

SWENGA Project


This web application is for maintaining and organizing a shared flat. It should make the life of a main tenant easier by providing a tool that makes it able to create, edit and export grocery lists so every roomer of the flat knows what kind of grocery is at home and what needs to be bought. Additionally, there will be a To-Do-Plan where To-Do’s can be added and marked with categories (e.g.: cleaning, appointments ...)


Team Members:
- Maximilian Steiner
- Nikolaus Spieß

## How To Install
1. Download the project repository from [here](https://github.com/MrBrown1992/COMMUNE-IC8/).
2. Open Eclipse and create a new Dynamic Web Project.
3. Convert the project to maven (RMB on project root -> Configure -> Convert to Maven)
4. Import project sources downloaded in step #1
5. Adapt the db.properties to your own database connection.
6. Configure the Server to host the Application (e.g. Tomcat).
7. Start the server.
8. In your web browser, navigate to http://localhost:8080/COMMUNE-IC8
9. If you set up a new database connection, you have to navigate to http://localhost:8080/COMMUNE-IC8/addUser and http://localhost:8080/COMMUNE-IC8/addCategories first. You will automatically be redirected to the login page.
10. You can know login as:

| Username        | Password           |
| ------------- |:-------------:|
| root      | root |
| admin      | password      |
| user | password      |

11. After the login you will be directed to the index.html and you can start using COMMUNE-IC8
