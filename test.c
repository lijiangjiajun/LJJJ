#include <stdio.h>
#include <stdlib.h>
int removeDuplicates(int* nums, int numsSize){
	long int tmp = 0;
	long int count = 0;
	int *p = nums;
	int *q = nums;
	while (q != (numsSize + nums))
	{
		while (*q == *(q + 1))
		{
			q++;
		}
		*p = *q;
		count++;
		p++;
		q++;
	}
	while (p<nums + numsSize)
	{
		*p = 0;
		p++;
	}
	return count;
}
int main()
{
	int a[9] = { 1, 1, 2,2,2,2,2,2,2};
	int ret=removeDuplicates(a, 9);
	printf("%d ", ret);
	return 0;

}
