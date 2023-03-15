/* Mohamed Asmali */
#include <stdbool.h>

void checkConstraints(double ball[],unsigned char hex);
double convertToRadians(double degree);
bool drainedBall(double ball[]);
void fixBallLeftWall(double ball[]);
void fixBallRightWall(double ball[]);
void fixPassedTopWall(double ball[]);
void getVelocityX(double ball[],double force);
void getVelocityY(double ball[],double force);
void move(double ball[],unsigned char hex);
bool passedLeftWall(double ball[]);
bool passedRightWall(double ball[]);
bool passedTopWall(double ball[]);
void updateX(double ball []);
void updateY(double ball[]);
void updateYVelocity(double ball[]);
