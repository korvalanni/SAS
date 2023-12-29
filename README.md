# Student-Auth-Service
Сервис авторизации для проектного практикума
Для сценариев логина и регистрации потребуется id проекта
- пользователь привязан к конкретному проекту
1) Завести проект или проверить его существование по id
   ![image](pics/create_project.png)
   ![image](pics/get_project.png)

# 1.Сценарий регистрации
1) Редиректнуть пользователя на страницу регистрации https://app.korvalanni:8080/registration?appId={id}
![image](pics/reg_forms.png)
2)Проверить статус пользователя с помощью
![image](pics/registration.png)

# 2.Сценарий логина
1) Редиректнуть пользователя на страницу логина https://app.korvalanni:8080/login?appId={id}
![image](pics/reg_forms.png)
2)Проверить статус пользователя с помощью
![image](pics/login.png)
3) При успешной авторизации получить пару access и refresh токенов

# 3. Механизм инвалидации и обновления токенов
![image](pics/token_inv.png)
![image](pics/token_refresh.png)

# 4. CRUD операции над пользователем 
админка для разработчика проекта
![image](pics/user_crud.png)
# 5. CRD операции над проектом
![image](pics/project_crud.png)

