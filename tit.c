#include <stdio.h>
int removeElement(int* nums, int numsSize, int val){
int i=0,k=0;
    for(i=0;i<numsSize;i++)
    {
        if(nums[i]!=val)
        {
            nums[k]=nums[i]; 
             k++;
        }
    }
    return k;
}

int main()
{
int a[4]={3,2,3,2};
int ret=removeElement(a,4,3);
printf("%d ",ret);
return 0;
}
