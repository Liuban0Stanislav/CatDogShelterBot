package pro.sky.telegrambot.exception;

public class BreedNotFoundException extends RuntimeException {
    private long id;

    public BreedNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
