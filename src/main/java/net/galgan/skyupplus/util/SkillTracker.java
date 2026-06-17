package net.galgan.skyupplus.util;

public class SkillTracker {
    public final String name;
    public int nextLevel;
    public double targetXP;
    public double avgRate = 0.0;
    public double previousXP;
    public double totalXPGain;
    public long startTime = 0;
    public long previousTime;
    public long lastUpdateTime;

    public SkillTracker(String name, double currentXP, long timeNow) {
        this.name = name;
        this.previousXP = currentXP;
        this.previousTime = timeNow;
        this.lastUpdateTime = timeNow;
    }
}