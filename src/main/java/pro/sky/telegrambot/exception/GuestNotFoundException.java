package pro.sky.telegrambot.exception;

public class GuestNotFoundException extends RuntimeException {
    private long id;

    public GuestNotFoundException(long id) {
        this.id = id;
    }

    public GuestNotFoundException(String message) {
        super(message);
    }

    public long getId() {
        return id;
    }
}
