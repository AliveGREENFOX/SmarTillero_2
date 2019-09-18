public class MedsFb
{
    String id, name, nMeds, tDays;

    public MedsFb(String id, String name, String nMeds, String tDays)
    {
        this.id = id;
        this.name = name;
        this.nMeds = nMeds;
        this.tDays = tDays;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getnMeds() {
        return nMeds;
    }

    public String gettDays() {
        return tDays;
    }
}
