package projecttoleankit;

public class Lane {
        String name = "";
        int index = 0;
        int activityId = -1;
        int topLevelParentLaneId = -1;
        int cardLimit = 0;
        int type = 0;
        int laneClassType = 1;
        int laneType = 1;
        int id = -1;
        boolean isDefaultDropLane = false;
        int classType = 0;
        int parentLaneId = -1;
        
    public void setName(String name){this.name = name;}
    public void setIndex(int index){this.index = index;}
    public void setActivityId(int activityId){this.activityId = activityId;}
    public void setTopLevelParentLaneId(int topLevelParentLaneId){this.topLevelParentLaneId = topLevelParentLaneId;}
    public void setCardLimit(int cardLimit){this.cardLimit = cardLimit;}
    public void setType(int type){this.type = type;}
    public void setLaneClassType(int laneClassType){this.laneClassType = laneClassType;}
    public void setLaneType(int laneType){this.laneType = laneType;}
    public void setId(int id){this.id = id;}
    public void setIsDefaultDropLane(boolean isDefaultDropLane){this.isDefaultDropLane = isDefaultDropLane;}
    public void setClassType(int classType){this.classType = classType;}
    public void setParentLaneId(int parentLaneId){this.parentLaneId = parentLaneId;}
    
    public String getName(){return name;}
    public int getIndex(){return index;}
    public int getActivityId(){return activityId;}
    public int getTopLevelParentLaneId(){return topLevelParentLaneId;}
    public int getCardLimit(){return cardLimit;}
    public int getType(){return type;}
    public int getLaneClassType(){return laneClassType;}
    public int getLaneType(){return laneType;}
    public int getId(){return id;}
    public boolean getIsDefaultDropLane(){return isDefaultDropLane;}
    public int getClassType(){return classType;}
    public int getParentLaneId(){return parentLaneId;}
}
