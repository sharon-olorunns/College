/**
 * Class FacebookCircles: calculates the statistics about the friendship circles in facebook data.
 *
 * @author Sharon Olorunniwo
 *
 * @version 01/12/15 02:03:28
 */
public class FacebookCircles {

    public int numberOfFacebookUsers;
    public int[] ID;
    WeightedQuickUnionUF facebookUnion;



    /**
     * Constructor
     * @param numberOfFacebookUsers : the number of users in the sample data.
     * Each user will be represented with an integer id from 0 to numberOfFacebookUsers-1.
     */
    public FacebookCircles(int numberOfFacebookUsers)
    {
        facebookUnion = new WeightedQuickUnionUF(numberOfFacebookUsers);
    }




    /**
     * creates a friendship connection between two users, represented by their corresponding integer ids.
     * @param userA : int id of first user
     * @param userB : int id of second  user
     */
    public void friends( int userA, int userB )
    {
        facebookUnion.union(userA, userB);
    }




    /**
     * @return the number of friend circles in the data already loaded.
     */
    public int numberOfCircles()
    {
        return facebookUnion.count();
    }



    /**
     * @return the size of the largest circle in the data already loaded.
     */
    public int sizeOfLargestCircle()
    {
        int largeCircle = 0;

        for (int i = 0; i < facebookUnion.size.length; i++)
        {
            if (largeCircle < facebookUnion.size[i])
            {
                largeCircle = facebookUnion.size[i];
            }
        }
        return largeCircle;
    }





    /**
     * @return the size of the median circle in the data already loaded.
     */
    public int sizeOfAverageCircle()
    {
        int average = 0;
        int total = numberOfCircles();

        for (int i = 0; i < facebookUnion.size.length; i++)
        {
            if (facebookUnion.parent[i] == i)
            {
                average += facebookUnion.size[i];
            }
        }
        return (average/total);
    }




    /**
     * @return the size of the smallest circle in the data already loaded.
     */
    public int sizeOfSmallestCircle()
    {
        int smallCircle = sizeOfLargestCircle();

        for (int i = 0; i < facebookUnion.size.length; i++)
        {
            if (facebookUnion.parent[i] == i)
            {
                if (smallCircle > facebookUnion.size[i])
                {
                    smallCircle = facebookUnion.size[i];
                }
            }
        }
        return smallCircle;
    }
}