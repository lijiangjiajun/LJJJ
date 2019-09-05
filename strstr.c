#include <stdio.h>
int strStr(char * haystack, char * needle){
    char* p=haystack;
    char* q=needle;
    char* start=haystack;
    int count=0;
    while(*p!='\0')
    {
        while(*p==*q)
        {
            p++;
            q++;
        }
        if(*q=='\0')
        {
            break;
        }
         start++;
        count++;
        if(*q!='\0')
        {
            p=start;
            q=needle;
        }
        
    }
    if(count!=0)
    return count;
    else
 return -1;
}
int main()
{
char  a[]="hello";
char b[]="ll";
int ret=strStr(a,b);
printf("%d ",ret);
return 0;
}
