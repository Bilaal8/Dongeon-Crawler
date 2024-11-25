public enum Direction {
    NORTH(2),SOUTH(0),EAST(3),WEST(1),NORTH_EAST(3),NORTH_WEST(1),SOUTH_EAST(3),SOUTH_WEST(1),NONE(0);
    private int frameLineNumber;

    Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}
