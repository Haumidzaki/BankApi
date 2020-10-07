package util;

import java.util.concurrent.atomic.AtomicLong;

public class GetNewId {
    private static AtomicLong id = new AtomicLong(100_006);

    private GetNewId() {
    }

    public static Long getNewId() {
        return id.getAndIncrement();
    }
}
