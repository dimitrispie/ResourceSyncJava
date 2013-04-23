package org.openarchives.resourcesync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapabilityList extends UrlSet
{
    private List<String> allowedCapabilities = new ArrayList<String>();

    public CapabilityList()
    {
        this(null, null);
    }

    public CapabilityList(String describedBy, Date lastModified)
    {
        super();
        this.capability = ResourceSync.CAPABILITY_CAPABILITYLIST;

        this.allowedCapabilities.add(ResourceSync.CAPABILITY_RESOURCELIST);
        this.allowedCapabilities.add(ResourceSync.CAPABILITY_RESOURCEDUMP);
        this.allowedCapabilities.add(ResourceSync.CAPABILITY_CHANGELIST);
        this.allowedCapabilities.add(ResourceSync.CAPABILITY_CHANGEDUMP);

        if (lastModified == null)
        {
            this.lastModified = new Date();
        }
        else
        {
            this.lastModified = lastModified;
        }

        if (describedBy != null)
        {
            this.addLn(ResourceSync.REL_DESCRIBED_BY, describedBy);
        }
    }

    public void addDescribedBy(String describedBy)
    {
        this.addLn(ResourceSync.REL_DESCRIBED_BY, describedBy);
    }

    public void addCapableUrl(URL url)
            throws SpecComplianceException
    {
        // first check to see if this is an allowed capability
        String capability = url.getCapability();
        if (capability == null || !this.allowedCapabilities.contains(url.getCapability()))
        {
            throw new SpecComplianceException("Attempting to add capability " + url.getCapability() + " to CapabilityList - not permitted.  " +
                    "CapabilityList may only represent resourcelist, resourcedump, changelist, changedump");
        }

        // now determine if a URL already exists with this capability
        List<ResourceSyncEntry> entries = this.getUrls();
        ResourceSyncEntry removable = null;
        for (ResourceSyncEntry entry : entries)
        {
            if (capability.equals(entry.getCapability()))
            {
                removable = entry;
                break; // have to break to avoid concurrent access/modification issues
            }
        }
        if (removable != null)
        {
            entries.remove(removable);
        }

        this.addUrl(url);
    }

    public URL addCapableUrl(String loc, String capability)
            throws SpecComplianceException
    {
        URL url = new URL();
        url.setLoc(loc);
        url.setCapability(capability);
        this.addCapableUrl(url);
        return url;
    }

    public URL setResourceList(String loc)
            throws SpecComplianceException
    {
        return this.addCapableUrl(loc, ResourceSync.CAPABILITY_RESOURCELIST);
    }

    public URL setResourceDump(String loc)
            throws SpecComplianceException
    {
        return this.addCapableUrl(loc, ResourceSync.CAPABILITY_RESOURCEDUMP);
    }

    public URL setChangeList(String loc)
            throws SpecComplianceException
    {
        return this.addCapableUrl(loc, ResourceSync.CAPABILITY_CHANGELIST);
    }

    public URL setChangeDump(String loc)
            throws SpecComplianceException
    {
        return this.addCapableUrl(loc, ResourceSync.CAPABILITY_CHANGEDUMP);
    }
}
