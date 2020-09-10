package ru.saprykinav.familyhub.bot;


public enum Messages {
    HELP ("Вход -- начало работы\nСемья -- просмотр страницы семьи\nДобавить покупку -- внести покупку в семейный бюджет"),
    ENTRY ("Набери Помощь для открытия списка доступных действий"),
    AUTHORIZATIONERROR ("Пользователь с таким именем не найден. Проверь, привязан ли твой телеграм аккаунт к Вашему профилю в системе\n"),
    SENDELSE ("Напиши что-нибудь другое"),
    ADDBUY ("Сколько она стоила?\nДля отмены введи Отмена"),
    CANCEL("Команда отменена. Для информации по доступным командам введи Помощь ");



    private String text;

    Messages(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }

}
