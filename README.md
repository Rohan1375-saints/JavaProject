<<<<<<< HEAD

# 📝 To-do List Application

This is your personal To-do List app to help you stay organized and on top of your tasks! 🎯✨

With this app, you can easily manage tasks that need to be completed and track your progress. It connects to a database to securely store your tasks, 🏷️ tags, and account information. 🔐

Key Features:

- ✅ Add, update, and delete tasks
 
- ✔️ Mark tasks as completed

- 🏷️ Organize tasks with tags

- 🔄 Sync and manage multiple accounts

Stay productive and keep everything in check with a user-friendly interface! 💪

  
## Prerequisites

Before you begin, ensure you have the following installed:

**1. Java Development Kit (JDK)** 

 - Version: 19 or higher

 - Ensure JAVA_HOME is correctly configured in your system environment
   variables.

 **2. Maven** 
 

 - Version: 3.8.0 or higher
 
 - Used for managing dependencies and building the project.

**3. MySQL**

 - Database Version: 8.0 or higher
 
 -  Required for database operations. Set up your MySQL instance and update connection details in the application configuration.

 **4. JavaFX SDK** 
 

 - Version: 19.0.2

 - Used for building the GUI of the application.

  **5. Maven will manage JavaFX dependencies, but ensure your system supports JavaFX runtime.**

  

## Getting Started

 
To run this project locally, follow these steps:

 **1. Clone the Repository**:
 
    
 ```
 git clone https://github.com/Rohan1375-saints/JavaProject
 cd ToDoList-J3
 ```
 
 **2. Open the Project:**
 
   Open the project in your preferred IDE(IntelliJ IDEA preferred)

 **3. Build the Project:**
 
 
 ```
 mvn clean install
 ```

 **4. SSH to SCWeb Server:**

```
ssh -L 3307:localhost:3306 DB_USERNAME@php.scweb.ca
```

Replace DB_USERNAME with your actual database username, then enter your login password.

 **5. Run the Project:**

Run `dblogin.java` in src>main>java>org.example>GUI>dblogin.java

 **6. Initializing db connection:**

 - For Database Name enter: [DB_USERNAME]md
 
 - For Database Username enter your own DB_USERNAME
 
 - For Database Password enter your own DB_LOGIN

 

 an example of a valid db credentials:


![dblogin.java image](https://i.ibb.co/s27KHJy/Screenshot-2024-12-06-at-9-12-09-PM.png)

then press the `connect` button.

Now u can use the program :)


## Database Schema

The app uses a relational database to store and manage tasks, tags, and user accounts. Below is the database schema:

### Tables


![User_Table](https://github.com/user-attachments/assets/e6863baf-bcc5-45f0-9f98-1830745817d4)

#### **1. Users**

| Column        | Data Type | Description                        |
|---------------|-----------|------------------------------------|
| `User_ID`     | INT       | Unique identifier for each user    |
| `User_name`   | VARCHAR   | Username for the account           |
| `Email`       | VARCHAR   | Email address for the user         |
| `Password`    | VARCHAR   | Password for authentication |
| `is_Premium`  | BOOLEAN   | Showing if a user is premium or not |


![Task_Table](https://github.com/user-attachments/assets/a5c8027d-bfb2-4022-aa83-0b5ee4259a83)

#### **2. Tasks**

| Column        | Data Type | Description                        |
|---------------|-----------|------------------------------------|
| `Task_ID`          | INT       | Unique identifier for each task    |
| `Task_Title`       | VARCHAR   | Title of the task                  |
| `Task_Description` | TEXT      | Detailed description of the task   |
| `Task_Due_Date`    | TIMESTAMP | Due date for the task              |
| `is_Completed`      | BOOLEAN      | Status of the task  |
| `is_Pinned`  | BOOLEAN | Showing if the task should be pinned or not |
| `deleted`  | BOOLEAN | Marking a task as a deleted one. |

![Tags_Table](https://github.com/user-attachments/assets/02a94a6a-7568-44a7-ad31-03fee77ff954)

#### **3. Tags**

| Column        | Data Type | Description                        |
|---------------|-----------|------------------------------------|
| `id`          | INT       | Unique identifier for each tag     |
| `name`        | VARCHAR   | Name of the tag                    |
| `created_at`  | TIMESTAMP | Timestamp when the tag was created |
| `updated_at`  | TIMESTAMP | Timestamp when the tag was last updated |


![Task_Tags_Table](https://github.com/user-attachments/assets/01c1d754-e026-4d9e-b95f-be65206b47e6)

#### **4. Task_Tags** (Many-to-many relationship between tasks and tags)

| Column        | Data Type | Description                        |
|---------------|-----------|------------------------------------|
| `Task_ID`     | INT       | Foreign key linking to the `Tasks` table |
| `Tag_ID`      | INT       | Foreign key linking to the `Tags` table |

---

### Relationships

- **Users ↔ Tasks**: A one-to-many relationship, where a user can have multiple tasks.
- **Tasks ↔ Tags**: A many-to-many relationship, where a task can have multiple tags and a tag can be applied to multiple tasks.


## 🖥️ Compatibility Chart

This project has been tested on the following platforms and operating systems:

| Platform       | Version           | Status        |
|----------------|-------------------|---------------|
| **Windows**    | Windows 11        | ✅ Tested and working |
| **macOS**      | macOS Sequoia 15.0 | ✅ Tested and working |
| **Linux**      | N/A               | ❌ Not Tested |
| **Other OS**   | N/A               | ❌ Not Tested |

---

We currently support Windows 11 and macOS Sequoia 15.0. Support for other platforms is not tested yet :))

## Authors

[@RohanParmar](https://github.com/rohan1375)


## License

MIT License

Copyright (c) 2024 PITA Group

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
=======
# JavaProject

Initial Repository
>>>>>>> 120306b5e6939884cb2fdf661e12b3536d3a82f1
