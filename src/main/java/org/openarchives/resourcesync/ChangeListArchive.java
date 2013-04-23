package org.openarchives.resourcesync;

import java.util.Date;

public class ChangeListArchive extends SitemapIndex
{
    public ChangeListArchive()
    {
        this(null, null);
    }

    public ChangeListArchive(Date lastMod)
    {
        this(lastMod, null);
    }

    public ChangeListArchive(Date lastMod, String capabilityList)
    {
        super();
        this.capability = ResourceSync.CAPABILITY_CHANGELIST;

        if (lastMod != null)
        {
            this.setLastModified(lastMod);
        }
        else
        {
            this.setLastModified(new Date());
        }

        if (capabilityList != null)
        {
            this.addLn(ResourceSync.REL_RESOURCESYNC, capabilityList);
        }
    }

    public void addChangeList(Sitemap sitemap)
    {
        this.addSitemap(sitemap);
    }

    public Sitemap addChangeList(String loc, Date lastMod)
    {
        Sitemap sitemap = new Sitemap();
        sitemap.setLoc(loc);
        sitemap.setLastModified(lastMod);
        this.addChangeList(sitemap);
        return sitemap;
    }
}
