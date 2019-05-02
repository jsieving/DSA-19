public class FirstFailingVersion {

    public static long binarySearch(long start, long end, IsFailingVersion version) {
        long mid = (start + end) / 2;
        System.out.printf("%d - | %d | - %d\n", start, mid, end);
        if (end - start <= 1) {
            return end;
        }
        if (version.isFailingVersion(mid)) {
            return binarySearch(start, mid, version);
        }
        else {
            return binarySearch(mid, end, version);
        }
    }

    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        return binarySearch(1, n, isBadVersion);
    }
}
