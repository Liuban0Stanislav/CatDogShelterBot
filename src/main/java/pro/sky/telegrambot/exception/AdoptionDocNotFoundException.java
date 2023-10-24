package pro.sky.telegrambot.exception;

public class AdoptionDocNotFoundException extends RuntimeException {
    private long id;

    public AdoptionDocNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
