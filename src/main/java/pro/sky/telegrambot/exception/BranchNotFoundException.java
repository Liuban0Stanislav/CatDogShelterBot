package pro.sky.telegrambot.exception;

public class BranchNotFoundException extends RuntimeException{
    private int id;

    public BranchNotFoundException() {
    }

    public BranchNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
