'''
Time complexity: O(n^2)
Space complexity: O(1)
'''
def solution_one(nums: List[int], target: int) -> List[int]:
  for i in range(len(nums)):
    for j in range(i+1, len(nums)):
      if (nums[i] + nums[j] == target):
        return [i,j]
  raise ValueError("Invalid Arguments, no solution exists")


'''
Time complexity: O(n log n)
Space complexity: O(n)
'''
def solution_two(nums: List[int], target: int) -> List[int]:
  # 1) Create a dictionary to store the original indexes of values in our input array
  original_indexes = dict()
  for i in range(len(nums)):
    ids = original_indexes.get(nums[i], [])
    ids.append(i)
    original_indexes[nums[i]] = ids

  # 2) Sort the array
  nums.sort()

  # 3) Use two pointers to converge on an valid two sum pair
  l = 0
  r = len(nums) - 1
  while (l < r):
    if (nums[l] + nums[r] == target):
      # if the number is the same then we'd need the 0th and 1st index. Otherwise, we
      # can use the 0th index for both of our numbers in the two sum pair
      if (nums[l] == nums[r]):
        id1 = original_indexes.get(nums[l])[0]
        id2 = original_indexes.get(nums[l])[1]
      else:
        id1 = original_indexes.get(nums[l])[0]
        id2 = original_indexes.get(nums[r])[0]
      return [id1, id2]
    elif (nums[l] + nums[r] < target):
      l += 1
    else:
      r -= 1

  raise ValueError("Invalid Arguments, no solution exists")


'''
Time complexity: O(n)
Space complexity: O(n)
'''
def solution_three(nums: List[int], target: int) -> List[int]:
  values_seen = dict() # keep track of values we have already seen and store there index as well
  for i in range(len(nums)):
    pair_val = target - nums[i]
    # When we find come across a number whose 'pair_val' we have already seen then we have found
    # a valid two sum pair!
    if (pair_val in values_seen):
      return [values_seen.get(pair_val), i]
    values_seen[nums[i]] = i
  raise ValueError("Invalid Arguments, no solution exists")