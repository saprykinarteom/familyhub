package ru.saprykinav.familyhub.bot;


public enum Messages {
    HELP ("Вход -- начало работы\nСемья -- просмотр страницы семьи\nДобавить покупку -- внести покупку в семейный бюджет\n"),
    ENTRY ("Набери Помощь для открытия списка доступных действий\n"),
    AUTHORIZATIONERROR ("Пользователь с таким именем не найден. Проверьте, привязан ли Ваш телеграм аккаунт к Вашему профилю в системе\n");


    private String text;

    Messages(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }

}
