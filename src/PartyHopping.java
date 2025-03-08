//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Party Hopping Visual Management System
// Course:   CS 300 Spring 2025
//
// Author:   Prateek Chand
// Email:    pchand2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Not Applicable
// Partner Email:   Not Applicable
// Partner Lecturer's Name: Not Applicable
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment. X
//   ___ We have both read and understand the course Pair Programming Policy. X
//   ___ We have registered our team prior to the team registration deadline. X
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:  No help used.
//
// Online Sources:  No source used
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import processing.core.PImage;

/**
 * This class contains methods to build the interface for creating, interacting, and moving agents
 * represented by static images.
 *
 * @author Prateek Chand
 */
public class PartyHopping {
    private static int bgColor;
    private static PImage[] locationImages;
    private static Agent[] agents;
    private static Party[] locations;

    /**
     * Main method that launches the application
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Utility.runApplication();
    }

    /**
     * Method to initialize application's background, load static image assets, and initialize
     * agents as well as locations where agents can move
     */
    public static void setup() {
        // System.out.println("Testing Setup");

        bgColor = Utility.color(81, 125, 168);

        locationImages = new PImage[3];
        locationImages[0] = Utility.loadImage("images" + File.separator + "cup.png");
        locationImages[1] = Utility.loadImage("images" + File.separator + "dice.png");
        locationImages[2] = Utility.loadImage("images" + File.separator + "ball.png");

        agents = new Agent[15];
        agents[0] = new Agent(Utility.loadImage("images" + File.separator + "student.png"));

        // System.out.println(agents[0].getX());
        // System.out.println(agents[0].width());

        Agent.setActiveImage(Utility.loadImage("images" + File.separator + "active.png"));

        locations = new Party[3];
        locations[0] = new Party('a', 200, 175, locationImages[0]);
        locations[1] = new Party('b', 600, 200, locationImages[1]);
        locations[2] = new Party('c', 400, 435, locationImages[2]);


    }

    /**
     * Method that repeatedly draws the application window and the current state of its contents to
     * the screen. This includes drawing the background, and static images for location and agents
     */
    public static void draw() {
        // System.out.println("Testing draw");

        Utility.background(bgColor);

        //Utility.image(locationImages[0], 200, 175);
        //Utility.image(locationImages[1], 600, 200);
        //Utility.image(locationImages[2], 400, 435);

        for (Party image : locations) {
            image.draw();
        }

        //System.out.println(isMouseOver(agents[0]));
        //agents[0].draw();


        // checks for and then draws the available agents in the array of Agents
        for (int index = 0; index < agents.length; ++index) {
            if (agents[index] != null) {
                agents[index].draw();
            }
        }

        // System.out.println(Utility.mouseX());
        // System.out.println(Utility.mouseX());

    }

    /**
     * Method to interact with specific agents on the screen. It checks if the mouse cursor is
     * hovering over any part of the agent image, including the blank space in the corners.
     *
     * @param agent - an object of type agent to check if the mouse cursor is hovering over it
     * @return returns true if the mouse is hovering the agent; otherwise, returns false
     */
    public static boolean isMouseOver(Agent agent) {
        float minimumXDimension = agent.getX() - (agent.width() / 2);
        float maximumXDimension = agent.getX() + (agent.width() / 2);

        float minimumYDimension = agent.getY() - (agent.height() / 2);
        float maximumYDimension = agent.getY() + (agent.height() / 2);

        // check if the mouse cursor is hovering within the x (horizontal) dimensions of the agent
        // image
        if ((Utility.mouseX() > minimumXDimension || Math.abs(Utility.mouseX() - minimumXDimension)
                < 0.0001) && (Utility.mouseX() < maximumXDimension || Math.abs(Utility.mouseX() -
                maximumXDimension) < 0.0001)) {

            // check if the mouse cursor is hovering within the y (vertical) dimensions of the
            // agent image
            if ((Utility.mouseY() > minimumYDimension || Math.abs(Utility.mouseY() -
                    minimumYDimension) < 0.0001) && (Utility.mouseY() < maximumYDimension ||
                    Math.abs(Utility.mouseY() - maximumYDimension) < 0.0001)) {
                return true;

            }
        }
        return false;

    }

    /**
     * Method to handle the event that the user is clicking the agent. The method loops through an
     * array of agents, checks if the mouse cursor is hovering over available agents in the array,
     * and only activates the agent at the highest index (i.e the last agent)
     */
    public static void mousePressed() {

        int maxIndex = -1;

        // checks for the highest index agent
        for (int currentIndex = 0; currentIndex < agents.length; ++currentIndex) {
            if (agents[currentIndex] != null && isMouseOver(agents[currentIndex])) {
                maxIndex = currentIndex;

                // System.out.println("current index: " + currentIndex + agents[currentIndex]
                // .isActive());
            }
        }

        if (maxIndex > -1) {
            agents[maxIndex].activate();

            // System.out.println("Max Index: " + maxIndex + agents[maxIndex].isActive());
        }
    }

    /**
     * Method to move and delete agents on the screen. It handles specific input commands from the
     * keyboard, where each key corresponds to a particular action.
     * <p>
     * Commands used: a - sends the active agent with the highest index to the location of the red
     * solo cup b - sends the active agent with the highest index to the location of the dice c -
     * sends the active agent with the highest index to the location of the football x - removes the
     * active agent with the highest index by setting its index to null
     *
     * @param keyStroke - character corresponding to the key on the keyboard that was pressed,
     *                  provided by PApplet whenever it detects the event of someone hitting a key
     *                  on the keyboard
     */
    public static void keyPressed(char keyStroke) {

        // compare keystroke with available location commands and set the destination of the
        // highest-index active agent to that location
        for (Party image : locations) {
            if (keyStroke == image.getID()) {
                for (int index = agents.length - 1; index >= 0; --index) {
                    if (agents[index] != null && agents[index].isActive()) {
                        agents[index].setDestination(image);
                        break;
                    }
                }
            }
        }

        // Add new agent when '.' is pressed and if the array of agents is not full
        if (keyStroke == '.') {
            for (int index = 0; index < agents.length; ++index) {
                if (agents[index] == null) {
                    agents[index] = new Agent(Utility.loadImage("images" + File.separator +
                            "student" + ".png"));
                    break;
                }
            }
        }

        // Delete the highest-index active agent when 'x' is pressed
        if (keyStroke == 'x') {
            for (int index = agents.length - 1; index >= 0; --index) {

                // System.out.println(index);
                // System.out.println(agents[index]);

                if (agents[index] != null && agents[index].isActive()) {

                    // System.out.println(index);
                    // System.out.println(agents[index]);
                    // System.out.println(agents[index].isActive());

                    agents[index] = null;

                    //System.out.println(agents[index]);
                    break;
                }
            }
        }

        /*
        //System.out.println(keyStroke);
        if (keyStroke == 'a') {
            for (int index = agents.length - 1; index >= 0; --index) {
                if (agents[index] != null && agents[index].isActive()) {
                    agents[index].setDestination(Agent.A);
                    break;
                }
            }
        }
        if (keyStroke == 'b') {
            for (int index = agents.length - 1; index >= 0; --index) {
                if (agents[index] != null && agents[index].isActive()) {
                    agents[index].setDestination(Agent.B);
                    break;
                }
            }
        }
        if (keyStroke == 'c') {
            for (int index = agents.length - 1; index >= 0; --index) {
                if (agents[index] != null && agents[index].isActive()) {
                    agents[index].setDestination(Agent.C);
                    break;
                }
            }
        }
        if (keyStroke == 'i') {
            for (int index = agents.length - 1; index >= 0; --index) {
                if (agents[index] != null && agents[index].isActive()) {
                    agents[index].setDestination(-1);
                }
            }
        }
       */
    }
}
