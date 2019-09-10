#include <stdio.h>
int maxsubArray(int* nums,int numsSize)
{
  int max=nums[0];
  int sum=0;
  int i=0;
  for(i=0;i<numsSize;;i++)
  {
    if(sum>=0)
    {
      sum=sum+nums[i];
    }
    else
    {
      sum=nums[i];
    }
    if(sum>max)
    {
      max=sum;
    }
  }
  return max;
}
