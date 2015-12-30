import java.awt.*;

/**
 * Created by john_bachman on 12/14/15.
 */

//A Group of Cells.
public class Colony {
    Cell[][] cells = new Cell[100][100];
    Cell[][] lastGeneration = new Cell[cells.length][cells[0].length];
    private int squareSize, widthBuffer, heightBuffer = 1;
    public int pen, population, unHealthyPopulation = 0;
    private String gameOfLife = "Game Of Life - Jack";
    private int gofStringIndex = 0;
    public boolean gridOn;
    public String colony_as_string;

    public Colony() {
        initCells();
        initLastGeneration();

        gridOn = false;

        for(int i = 0; i < 1000; i++) {
            lastGeneration[(int)(Math.random() * cells.length)][(int)(Math.random() * cells[0].length)] = new Cell(1);
        }
    }

    protected void fillArray() {
        for(int i = 0; i < 1000; i++) {
            int randomR = (int)(Math.random() * cells.length);
            int randomC = (int)(Math.random() * cells[0].length);
            lastGeneration[randomR][randomC] = new Cell(1);
            cells[randomR][randomC] = new Cell(1);
        }
    }

    protected void initLastGeneration() {
        for(int r = 0; r < lastGeneration.length; r++) {
            for(int c = 0; c < lastGeneration[0].length; c++) {
                lastGeneration[r][c] = new Cell(0);
            }
        }
    }

    //Used for debugging.
    @Deprecated
    private void print2Darray(Cell[][] array) {
        System.out.print("[");
        for(int r = 0; r < array.length; r++) {
            System.out.print("[");
            for(int c = 0; c  < array[0].length; c++) {
                System.out.print(array[r][c].getStatus() + " ");
            }
            System.out.print("]\n");
        }
        System.out.print("]");
    }

    protected void initCells() {
        for(int r = 0; r < cells.length; r++) {
            for(int c = 0; c < cells[0].length; c++) {
                cells[r][c] = new Cell(0);
            }
        }
    }

    private Info checkIfNeighbor(int r, int c) {
        Info info = new Info(0, false, false);
        if(r >= 0 && r < lastGeneration.length && c >= 0 && c < lastGeneration[0].length) {
            if(lastGeneration[r][c].getStatus() > 0) {
                if(lastGeneration[r][c].isCured()) {
                    info.cured = true;
                } else if(lastGeneration[r][c].isDiseased()) {
                    info.diseased = true;
                }
                info.neighbors++;
            }
        }
        return info;
    }

    protected void step() {
        for(int r = 0; r < lastGeneration.length; r++) {
            for(int c = 0; c < lastGeneration[0].length; c++) {
                if (cells[r][c].getStatus() != -1) {
                    InfoCollection infoCollection = new InfoCollection();

                    infoCollection.addInfo(checkIfNeighbor(r - 1, c));
                    infoCollection.addInfo(checkIfNeighbor(r + 1, c));
                    infoCollection.addInfo(checkIfNeighbor(r, c + 1));
                    infoCollection.addInfo(checkIfNeighbor(r, c - 1));
                    infoCollection.addInfo(checkIfNeighbor(r - 1, c - 1));
                    infoCollection.addInfo(checkIfNeighbor(r - 1, c + 1));
                    infoCollection.addInfo(checkIfNeighbor(r + 1, c + 1));
                    infoCollection.addInfo(checkIfNeighbor(r + 1, c - 1));

                    if ((infoCollection.getNeighbors() < 2) || (infoCollection.getNeighbors() > 3)) {
                        cells[r][c].setStatus(0);
                        cells[r][c].setDiseased(false);
                        cells[r][c].setCured(false);
                    } else if (infoCollection.getNeighbors() == 3 && cells[r][c].getStatus() != -1) {
                        cells[r][c].age();
                        if(infoCollection.isCured() && infoCollection.isDiseased()) {
                            cells[r][c].setCured(true);
                        } else if (infoCollection.isDiseased() && !infoCollection.isCured()) {
                            cells[r][c].setDiseased(true);
                        }
                    } else if (lastGeneration[r][c].getStatus() > 0) {
                        cells[r][c].age();
                        if(cells[r][c].isCured() && !infoCollection.isDiseased()) {
                            cells[r][c].setCured(false);
                        }
                        if(infoCollection.isCured()) {
                            cells[r][c].setCured(true);
                        } else if (infoCollection.isDiseased() && !infoCollection.isCured()) {
                            cells[r][c].setDiseased(true);
                        }
                    }
                }
            }
        }

        for(int r = 0; r < cells.length; r++) {
            for(int c = 0; c < cells[0].length; c++) {
                lastGeneration[r][c].setStatus(cells[r][c].getStatus());
                lastGeneration[r][c].setDiseased(cells[r][c].isDiseased());
                lastGeneration[r][c].setCured(cells[r][c].isCured());
            }
        }
    }

    protected void drawColony(Graphics2D g2, int width, int height, boolean opacityOn) {
        squareSize = height / cells.length;
        widthBuffer = (width - (squareSize * cells[0].length)) / 2;
        heightBuffer = (height - (squareSize * cells.length)) / 2;
        population = 0;
        unHealthyPopulation = 0;
        gofStringIndex++;
        if(gofStringIndex > gameOfLife.length()) {
            gofStringIndex = 0;
        }
        g2.setFont(new Font("Arial", Font.BOLD, heightBuffer));
        g2.setColor(Color.GREEN);
        g2.drawString(gameOfLife.substring(0, gofStringIndex), widthBuffer, heightBuffer - 10);
        int opacity = 0;
        if(opacityOn) {
            opacity = 150;
        } else {
            opacity = 255;
        }
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                if(cells[r][c].isCured()) {
                    population++;
                    g2.setColor(new Color(255, 39, 224, opacity));
                } else if(cells[r][c].isDiseased()) {
                    population++;
                    unHealthyPopulation++;
                    g2.setColor(new Color(102, 0, 204, opacity));
                }  else if(cells[r][c].getStatus() == -1) {
                    g2.setColor(Color.GRAY);

                } else if(cells[r][c].getStatus() > 0 && cells[r][c].getStatus() < 51) {
                    population++;
                    g2.setColor(new Color(0, 255 - cells[r][c].getStatus() * 5, 0, opacity));
                } else if(cells[r][c].getStatus() > 51) {
                    population++;
                    g2.setColor(new Color(0, 0, 0, opacity));
                } else {
                    g2.setColor(new Color(255,255,255,opacity));
                }
                g2.fillRect(widthBuffer + c * squareSize, heightBuffer + r * squareSize, squareSize, squareSize);
            }
        }

        if(gridOn) {
            g2.setColor(Color.BLACK);
            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells[0].length; c++) {
                    g2.drawRect(widthBuffer + c * squareSize, heightBuffer + r * squareSize, squareSize, squareSize);
                }
            }
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.BLACK);
        String penType = "Unknown";
        switch (pen) {
            case 0:
                penType = "Cell";
                break;
            case 1:
                penType = "Disease";
                break;
            case 2:
                penType = "Delete / Annihilator";
                break;
            case 3:
                penType = "Wall";
                break;
            case 4:
                penType = "Cure";
                break;
            case 5:
                penType = "Information On Cell";
                break;
        }
        g2.drawString("Current pen type: " + penType, 0, 15);
        g2.drawString("Population: " + population, 0, 30);
        g2.drawString("Healthy Population: " + (population - unHealthyPopulation), 0, 45);
        g2.drawString("Unhealthy Population: " + unHealthyPopulation, 0, 60);
    }

    protected void placeCell(int MouseX, int MouseY) {
        int column = (MouseX - widthBuffer) / squareSize;
        int row = (MouseY - heightBuffer) / squareSize;
        if(row >= 0 && column >= 0 && row < cells.length && column < cells[0].length) {
            if (pen == 0) {
                cells[row][column].setStatus(1);
                lastGeneration[row][column].setStatus(1);
            } else if (pen == 1) {
                if (cells[row][column].getStatus() > 0) {
                    cells[row][column].setDiseased(true);
                }
            } else if (pen == 2) {
                cells[row][column].setStatus(0);
                cells[row][column].setCured(false);
                cells[row][column].setDiseased(false);
                lastGeneration[row][column].setStatus(0);
                lastGeneration[row][column].setDiseased(false);
                lastGeneration[row][column].setCured(false);

            } else if (pen == 3) {
                cells[row][column].setStatus(-1);
                cells[row][column].setCured(false);
                cells[row][column].setDiseased(false);
                lastGeneration[row][column].setStatus(-1);
                lastGeneration[row][column].setDiseased(false);
                lastGeneration[row][column].setCured(false);
            } else if(pen == 4) {
                if(cells[row][column].getStatus() > 0) {
                    cells[row][column].setCured(true);
                }
            } else if(pen == 5) {
                if(cells[row][column].getStatus() > 0) {
                    BioFrame bioFrame = new BioFrame();
                }
            }
        }
    }
}