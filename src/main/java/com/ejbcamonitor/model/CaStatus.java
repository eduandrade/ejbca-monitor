package com.ejbcamonitor.model;

public class CaStatus implements Comparable<CaStatus> {
    
    private String name;
    
    private boolean isAllOk;
    
    private String message;
    
    public CaStatus() {
    }
    
    public CaStatus(String name, boolean isAllOk, String message) {
        this.name = name;
        this.isAllOk = isAllOk;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllOk() {
        return isAllOk;
    }

    public void setAllOk(boolean isAllOk) {
        this.isAllOk = isAllOk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CaStatus [name=" + name + ", isAllOk=" + isAllOk + ", message=" + message + "]";
    }

    @Override
    public int compareTo(CaStatus caStatus) {
        return this.name.compareToIgnoreCase(caStatus.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isAllOk ? 1231 : 1237);
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CaStatus other = (CaStatus) obj;
        if (isAllOk != other.isAllOk)
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
