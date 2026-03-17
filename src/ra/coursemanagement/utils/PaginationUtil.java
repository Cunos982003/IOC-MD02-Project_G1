package ra.coursemanagement.utils;

public class PaginationUtil {

    private PaginationUtil() {
    }

    public static int totalPages(int totalItems, int pageSize) {
        if (pageSize <= 0) return 0;
        if (totalItems <= 0) return 0;
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public static int clampPage(int currentPage, int totalPages) {
        if (totalPages <= 0) return 1;
        if (currentPage < 1) return 1;
        if (currentPage > totalPages) return totalPages;
        return currentPage;
    }
}


