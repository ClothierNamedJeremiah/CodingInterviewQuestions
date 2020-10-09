class Solution:
    '''
    Time complexity: O(n log n)
    Space complexity: O(n)
    '''
    def maxSumRangeQuery(self, nums: List[int], requests: List[List[int]]) -> int:
        MOD = 1000000000 + 7;
        freq = [0 for _ in range(len(nums))]
        for l, r in requests:
            freq[l] += 1
            if (r + 1 < len(nums)):
                freq[r+1] -= 1
        
        for i in range(1, len(nums)):
            freq[i] += freq[i-1]
        
        nums.sort()
        freq.sort()
        
        sum = 0
        for i in range(len(nums)):
            sum += nums[i] * freq[i]
        
        return sum % MOD