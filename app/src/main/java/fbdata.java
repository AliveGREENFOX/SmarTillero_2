
public class fbdata
{
    String name, nMeds, tDay, pTime;

    public fbdata(String name, String nMeds, String tDay, String pTime)
    {
        this.name = name;
        this.nMeds = nMeds;
        this.tDay = tDay;
        this.pTime = pTime;
    }
    public String getName()
    {
        return name;
    }

    public String getnMeds()
    {
        return nMeds;
    }

    public String gettDay()
    {
        return tDay;
    }

    public String getpTime()
    {
        return pTime;
    }
}
