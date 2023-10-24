package pro.sky.telegrambot.exception;

public class PetNotFoundException extends RuntimeException {
    private long id;

    public PetNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
