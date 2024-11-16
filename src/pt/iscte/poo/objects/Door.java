package pt.iscte.poo.objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends Object{
    public String getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(String nextRoom) {
        this.nextRoom = nextRoom;
    }

    private String nextRoom;

    public Door(Point2D position, String nextRoom) {
        super(position);
        this.nextRoom = nextRoom;
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
