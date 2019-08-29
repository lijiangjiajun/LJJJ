#include <stdio.h>
#include <unistd.h>
int main()
{
  int i;
  char a[101]={'\0'};
  char b[100]={'|','/','-','\\'};
   for(i=0;i<=100;i++)
   {
a[i]='#';
printf("[%-100s]%d%% %c\r",a,i,b[i%4]);
   fflush(stdout);
   usleep(100000);
}
printf("\n");
return 0;
}
