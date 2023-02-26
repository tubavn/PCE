package Visualization;

/**
 * Created by Admin on 21/08/2016.
 */
public class Edge
{
    private final String name;

    public Edge(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}