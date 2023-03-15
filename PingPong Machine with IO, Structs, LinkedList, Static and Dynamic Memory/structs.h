// Mohamed Asmali
//ball struct
struct Baller{
    double X;
    double Y;
    double VX;
    double VY;
    double THETA;
    double FORCE;
    unsigned char bits;
    struct simData *sim;
};
typedef struct Baller Ball;
//sim struct
struct simData{
    int score;
    double elapsedTime;
    void *inPlay;
    void *off_Play;
};
typedef struct simData simData;
