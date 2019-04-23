package Structs;

public enum SecurityLevel
{
    READONLY(0),NORMAL(1),HIGH(2);
    int value;
    SecurityLevel(int v)
    {
        value =v;
    }
}
