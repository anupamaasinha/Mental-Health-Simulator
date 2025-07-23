# MindfulMoments - Mental Health Journaling App

A comprehensive Java Swing-based mental health application that helps users track their daily moods, journal their thoughts, and receive personalized wellness suggestions through self-reflection.

## 🌟 Features

### Core Functionality
- **Daily Mood Check-ins**: Track emotions with 10 different mood types, intensity levels (1-10), and emotion tagging
- **Guided Journaling**: Free-form writing and 10+ guided prompts for self-reflection
- **Personalized Suggestions**: AI-driven recommendations based on mood patterns (breathing exercises, mindfulness tasks, writing prompts)
- **Wellness Dashboard**: Visual insights showing mood history, journaling consistency, and emotional trends
- **Onboarding Experience**: Educational introduction to the app's mental health goals

### Technical Features
- **GUI Interface**: Built with Java Swing and AWT for a user-friendly experience
- **Local Data Storage**: All data stored locally using ArrayList and HashMap collections
- **Pattern Recognition**: Detects mood trends and sudden changes for personalized care
- **Modular Architecture**: Clean separation of concerns across different services

## 🏗️ Architecture

The application follows a modular design pattern with clear separation of responsibilities:

```
MentalHealthApp (Main GUI)
├── OnboardingService (User introduction and setup)
├── MoodCheckInService (Daily mood tracking)
├── JournalingService (Writing and reflection tools)
├── SuggestionEngine (Pattern analysis and recommendations)
├── DashboardService (Data visualization and insights)
└── DataManager (Local data storage with ArrayList)
```

### Core Data Models
- **User**: Basic user information and join date
- **MoodLog**: Comprehensive mood data with timestamps, intensity, tags, and notes
- **JournalEntry**: Structured journal entries with titles, content, and tags
- **MoodType**: Enum with 10 emotional states, values, and emoji representations

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code, etc.) or command line

### Installation & Setup

1. **Clone or Download**
   ```bash
   # If using git
   git clone [repository-url]
   
   # Or download the source files directly
   ```

2. **Compile the Application**
   ```bash
   javac MentalHealthApp.java
   ```

3. **Run the Application**
   ```bash
   java MentalHealthApp
   ```

### File Structure
```
project-folder/
└── MentalHealthApp.java    # Complete application code
└── README.md              # This documentation file
```

## 🎯 How to Use

### First Time Setup
1. Launch the application
2. Complete the onboarding process by entering your name
3. Learn about the app's mental health goals and features

### Daily Usage

#### 1. Mood Check-in
- Select from 10 mood types (Very Happy, Happy, Neutral, Sad, Anxious, etc.)
- Rate intensity on a 1-10 scale
- Add emotion tags (grateful, overwhelmed, hopeful, etc.)
- Include optional notes about your day
- Receive personalized suggestions after check-in

#### 2. Journaling
- Choose between free-form writing or guided prompts
- Sample prompts include:
  - "What made you smile today?"
  - "Describe a challenge you faced and how you handled it"
  - "What are you grateful for right now?"
- Add tags to organize entries
- All entries are timestamped automatically

#### 3. Dashboard Insights
- View mood summary with averages and patterns
- Track check-in and journaling streaks
- Analyze 7-day mood trends
- See most frequently tagged emotions
- Monitor progress over time

#### 4. Personalized Suggestions
Based on your mood patterns, receive:
- **For Anxiety**: Breathing techniques, meditation, gentle walks
- **For Sadness**: Social connection tips, light exposure, comforting activities
- **For Stress**: Time management, relaxation techniques, priority lists
- **For Happiness**: Gratitude practices, goal-setting, sharing positivity

## 🛠️ Technical Implementation

### Data Storage
- **Local Only**: All data remains on your device
- **ArrayList Collections**: User data, mood logs, and journal entries
- **No Authentication**: Simplified for local use
- **No Encryption**: Basic storage for development version

### GUI Components
- **Swing Framework**: Professional desktop application interface
- **CardLayout Navigation**: Smooth transitions between screens
- **Custom Styling**: Mental health-focused color scheme (Alice Blue, Steel Blue)
- **Responsive Design**: Adapts to different screen interactions

### Pattern Analysis
The suggestion engine analyzes:
- Recent mood trends (7-day patterns)
- Sudden mood changes or declines
- Journaling consistency vs. mood tracking
- Most common emotions and triggers

## 🎨 Design Choices

### User Experience
- **Calming Color Palette**: Blues and light colors promote tranquility
- **Emoji Integration**: Visual mood representations for quick recognition
- **Progressive Disclosure**: Information revealed as needed to prevent overwhelm
- **Positive Reinforcement**: Encouraging messages and streak tracking

### Mental Health Focus
- **Non-judgmental Language**: Supportive and understanding tone throughout
- **Privacy First**: All data stays local for user comfort
- **Flexible Input**: Optional fields reduce pressure on users
- **Educational Approach**: Onboarding explains benefits of self-reflection

### Technical Decisions
- **Java Swing**: Cross-platform compatibility and familiar desktop experience
- **ArrayList Storage**: Simple, effective for local data without database complexity
- **Modular Services**: Easy to extend and maintain individual features
- **Event-driven UI**: Responsive interface that reacts to user actions

## 🔮 Future Enhancements

### Potential Features
- **Data Export**: Export mood and journal data to CSV or PDF
- **Reminder System**: Configurable notifications for daily check-ins
- **Advanced Analytics**: Longer-term trend analysis and correlations
- **Customizable Themes**: User-selectable color schemes and layouts
- **Goal Setting**: Personal wellness goals and progress tracking

### Technical Improvements
- **Database Integration**: Persistent storage with SQLite or H2
- **Data Encryption**: Secure storage for sensitive mental health data
- **User Authentication**: Multi-user support with login system
- **Cloud Sync**: Optional backup and sync across devices
- **Mobile Version**: Android/iOS companion apps

## 🤝 Contributing

This is an educational project focused on mental health awareness through technology. Contributions should prioritize:

1. **User Safety**: Features should promote positive mental health
2. **Privacy**: Maintain local-first data storage approach  
3. **Accessibility**: Ensure the app is usable by people with different needs
4. **Evidence-based**: Suggestions should be grounded in mental health best practices

## ⚠️ Important Notes

### Mental Health Disclaimer
- This app is for **self-reflection and wellness tracking only**
- It is **not a substitute for professional mental health care**
- If experiencing serious mental health concerns, please consult a qualified healthcare provider
- In crisis situations, contact local emergency services or mental health hotlines

### Data Privacy
- All data is stored locally on your device
- No information is transmitted to external servers
- Users are responsible for backing up their own data
- Uninstalling the app will permanently delete all data

### System Requirements
- **Operating System**: Windows, macOS, or Linux with Java support
- **Java Version**: JDK 8 or higher
- **Memory**: Minimum 512MB RAM available for Java applications
- **Storage**: Minimal disk space required (data grows with usage)

## 📝 License

This project is created for educational purposes. Feel free to use, modify, and distribute while maintaining the focus on positive mental health outcomes.

## 📞 Support

For technical issues or questions about the application:
1. Check that Java is properly installed and updated
2. Verify all source code is in a single directory
3. Ensure proper compilation before running
4. Review console output for any error messages

---

**Remember**: Taking care of your mental health is a journey, not a destination. Small, consistent steps toward self-awareness can lead to meaningful positive changes. 💙

*MindfulMoments - Your companion in building healthier mental habits, one day at a time.*
