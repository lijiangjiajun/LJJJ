#include <stdio.h>
int romanToInt(char * s){
	int map[128];
	map['I'] = 1;
	map['V'] = 5;
	map['X'] = 10;
	map['L'] = 50;
	map['C'] = 100;
	map['D'] = 500;
	map['M'] = 1000;
	int ret = 0;
	while (*s != '\0')
	{
		if (map[*s]<map[*(s + 1)])
		{
			ret = ret + map[*(s + 1)] - map[*(s)];
			s = s + 2;
		}
		else
		{
			ret = ret + map[*s];
			s++;
		}
	}
	return ret;
}

int main()
{
	char a[6] = "III";
	int t=romanToInt(a);
	printf("%d ", t);
	return 0;
}
