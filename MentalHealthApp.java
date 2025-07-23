// Main Application Entry Point
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MentalHealthApp {
    private DataManager dataManager;
    private OnboardingService onboardingService;
    private MoodCheckInService moodCheckInService;
    private JournalingService journalingService;
    private SuggestionEngine suggestionEngine;
    private DashboardService dashboardService;
    private Scanner scanner;
    
    public MentalHealthApp() {
        this.dataManager = new DataManager();
        this.onboardingService = new OnboardingService();
        this.moodCheckInService = new MoodCheckInService();
        this.journalingService = new JournalingService();
        this.suggestionEngine = new SuggestionEngine();
        this.dashboardService = new DashboardService(dataManager);
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Welcome to MindfulMoments - Your Mental Health Companion");
        
        // Check if user exists or needs onboarding
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            currentUser = onboardingService.conductOnboarding(scanner);
            dataManager.createUser(currentUser);
        }
        
        mainMenu(currentUser);
    }
    
    private User getCurrentUser() {
        // In a real app, this would check for existing user data
        // For simplicity, we'll create a new user each time
        return null;
    }
    
    private void mainMenu(User user) {
        while (true) {
            System.out.println("\n=== MindfulMoments Menu ===");
            System.out.println("1. Daily Mood Check-in");
            System.out.println("2. View Dashboard");
            System.out.println("3. Journal Entry");
            System.out.println("4. Get Suggestions");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    performMoodCheckIn(user);
                    break;
                case 2:
                    dashboardService.displayDashboard(user);
                    break;
                case 3:
                    performJournaling(user);
                    break;
                case 4:
                    displaySuggestions(user);
                    break;
                case 5:
                    System.out.println("Take care! Remember to check in tomorrow.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private void performMoodCheckIn(User user) {
        MoodLog moodLog = moodCheckInService.conductMoodCheckIn(scanner);
        dataManager.addMoodLog(user.getUserId(), moodLog);
        
        // Show suggestions after check-in
        List<String> suggestions = suggestionEngine.generateSuggestions(user, dataManager);
        System.out.println("\n=== Personalized Suggestions ===");
        suggestions.forEach(System.out::println);
    }
    
    private void performJournaling(User user) {
        JournalEntry entry = journalingService.createJournalEntry(scanner);
        dataManager.addJournalEntry(user.getUserId(), entry);
        System.out.println("Journal entry saved successfully!");
    }
    
    private void displaySuggestions(User user) {
        List<String> suggestions = suggestionEngine.generateSuggestions(user, dataManager);
        System.out.println("\n=== Your Personalized Suggestions ===");
        suggestions.forEach(System.out::println);
    }
    
    public static void main(String[] args) {
        new MentalHealthApp().start();
    }
}

// Core Data Models
class User {
    private String userId;
    private String name;
    private LocalDateTime joinDate;
    
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.joinDate = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public LocalDateTime getJoinDate() { return joinDate; }
}

enum MoodType {
    VERY_HAPPY(5, "Very Happy", "😊"),
    HAPPY(4, "Happy", "🙂"),
    NEUTRAL(3, "Neutral", "😐"),
    SAD(2, "Sad", "😢"),
    VERY_SAD(1, "Very Sad", "😭"),
    ANXIOUS(2, "Anxious", "😰"),
    STRESSED(2, "Stressed", "😫"),
    ANGRY(2, "Angry", "😠"),
    EXCITED(4, "Excited", "🤩"),
    CALM(4, "Calm", "😌");
    
    private final int value;
    private final String displayName;
    private final String emoji;
    
    MoodType(int value, String displayName, String emoji) {
        this.value = value;
        this.displayName = displayName;
        this.emoji = emoji;
    }
    
    public int getValue() { return value; }
    public String getDisplayName() { return displayName; }
    public String getEmoji() { return emoji; }
}

class MoodLog {
    private LocalDateTime timestamp;
    private MoodType mood;
    private List<String> emotionTags;
    private String notes;
    private int intensityLevel; // 1-10 scale
    
    public MoodLog(MoodType mood, List<String> emotionTags, String notes, int intensityLevel) {
        this.timestamp = LocalDateTime.now();
        this.mood = mood;
        this.emotionTags = new ArrayList<>(emotionTags);
        this.notes = notes;
        this.intensityLevel = intensityLevel;
    }
    
    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public MoodType getMood() { return mood; }
    public List<String> getEmotionTags() { return new ArrayList<>(emotionTags); }
    public String getNotes() { return notes; }
    public int getIntensityLevel() { return intensityLevel; }
}

class JournalEntry {
    private LocalDateTime timestamp;
    private String content;
    private List<String> tags;
    private String title;
    
    public JournalEntry(String title, String content, List<String> tags) {
        this.timestamp = LocalDateTime.now();
        this.title = title;
        this.content = content;
        this.tags = new ArrayList<>(tags);
    }
    
    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getContent() { return content; }
    public List<String> getTags() { return new ArrayList<>(tags); }
    public String getTitle() { return title; }
}

// Data Storage and Management
class DataManager {
    private ArrayList<User> users;
    private HashMap<String, ArrayList<MoodLog>> userMoodLogs;
    private HashMap<String, ArrayList<JournalEntry>> userJournalEntries;
    
    public DataManager() {
        this.users = new ArrayList<>();
        this.userMoodLogs = new HashMap<>();
        this.userJournalEntries = new HashMap<>();
    }
    
    public void createUser(User user) {
        users.add(user);
        userMoodLogs.put(user.getUserId(), new ArrayList<>());
        userJournalEntries.put(user.getUserId(), new ArrayList<>());
    }
    
    public void addMoodLog(String userId, MoodLog moodLog) {
        userMoodLogs.get(userId).add(moodLog);
    }
    
    public void addJournalEntry(String userId, JournalEntry entry) {
        userJournalEntries.get(userId).add(entry);
    }
    
    public List<MoodLog> getMoodLogs(String userId) {
        return new ArrayList<>(userMoodLogs.getOrDefault(userId, new ArrayList<>()));
    }
    
    public List<JournalEntry> getJournalEntries(String userId) {
        return new ArrayList<>(userJournalEntries.getOrDefault(userId, new ArrayList<>()));
    }
    
    public List<MoodLog> getRecentMoodLogs(String userId, int days) {
        List<MoodLog> allLogs = getMoodLogs(userId);
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        
        return allLogs.stream()
            .filter(log -> log.getTimestamp().isAfter(cutoff))
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}

// Onboarding Service
class OnboardingService {
    
    public User conductOnboarding(Scanner scanner) {
        System.out.println("\n=== Welcome to MindfulMoments ===");
        System.out.println("Your journey to better mental health starts here!");
        System.out.println();
        System.out.println("MindfulMoments helps you:");
        System.out.println("• Track your daily moods and emotions");
        System.out.println("• Reflect through guided journaling");
        System.out.println("• Discover patterns in your mental wellness");
        System.out.println("• Receive personalized suggestions for well-being");
        System.out.println();
        System.out.println("The goal is simple: By taking a few minutes each day to check in");
        System.out.println("with yourself, you'll develop greater emotional awareness and");
        System.out.println("discover what helps you feel your best.");
        System.out.println();
        
        System.out.print("What's your name? ");
        String name = scanner.nextLine();
        
        System.out.println("\nHi " + name + "! Let's get you started on your wellness journey.");
        System.out.println("Remember: This is a safe space for honest self-reflection.");
        System.out.println("The more genuine you are, the more helpful your insights will be.");
        
        String userId = "user_" + System.currentTimeMillis();
        return new User(userId, name);
    }
}

// Mood Check-in Service
class MoodCheckInService {
    
    public MoodLog conductMoodCheckIn(Scanner scanner) {
        System.out.println("\n=== Daily Mood Check-in ===");
        System.out.println("How are you feeling today?");
        
        // Display mood options
        MoodType[] moods = MoodType.values();
        for (int i = 0; i < moods.length; i++) {
            System.out.printf("%d. %s %s%n", 
                i + 1, moods[i].getEmoji(), moods[i].getDisplayName());
        }
        
        System.out.print("Select your mood (1-" + moods.length + "): ");
        int moodChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        MoodType selectedMood = moods[Math.max(0, Math.min(moodChoice, moods.length - 1))];
        
        // Get intensity level
        System.out.print("How intense is this feeling? (1-10): ");
        int intensity = Math.max(1, Math.min(10, scanner.nextInt()));
        scanner.nextLine(); // consume newline
        
        // Get emotion tags
        System.out.println("Any specific emotions you'd like to tag? (comma-separated, or press enter to skip):");
        System.out.println("Examples: grateful, overwhelmed, hopeful, frustrated, energetic, tired");
        String emotionInput = scanner.nextLine().trim();
        
        List<String> emotionTags = new ArrayList<>();
        if (!emotionInput.isEmpty()) {
            String[] tags = emotionInput.split(",");
            for (String tag : tags) {
                emotionTags.add(tag.trim().toLowerCase());
            }
        }
        
        // Get optional notes
        System.out.println("Anything specific about today you'd like to note? (optional):");
        String notes = scanner.nextLine().trim();
        
        System.out.println("✓ Mood check-in complete! Thank you for taking time for yourself.");
        
        return new MoodLog(selectedMood, emotionTags, notes, intensity);
    }
}

// Journaling Service
class JournalingService {
    private static final List<String> JOURNAL_PROMPTS = Arrays.asList(
        "What made you smile today?",
        "Describe a challenge you faced and how you handled it.",
        "What are you grateful for right now?",
        "How do you want to feel tomorrow?",
        "What's one thing you learned about yourself today?",
        "Describe a moment when you felt truly present.",
        "What would you tell a friend who's going through what you're experiencing?",
        "What's something you're looking forward to?",
        "How did you show kindness to yourself or others today?",
        "What thoughts have been occupying your mind lately?"
    );
    
    public JournalEntry createJournalEntry(Scanner scanner) {
        System.out.println("\n=== Journal Entry ===");
        System.out.println("1. Free writing");
        System.out.println("2. Use a guided prompt");
        System.out.print("Choose your journaling style: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String prompt = "";
        if (choice == 2) {
            prompt = getRandomPrompt();
            System.out.println("\nPrompt: " + prompt);
            System.out.println();
        }
        
        System.out.print("Entry title (optional): ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            title = "Entry - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        }
        
        System.out.println("Write your thoughts (press Enter twice when finished):");
        StringBuilder content = new StringBuilder();
        String line;
        int emptyLines = 0;
        
        while (emptyLines < 2) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                emptyLines++;
            } else {
                emptyLines = 0;
            }
            content.append(line).append("\n");
        }
        
        System.out.print("Add tags (comma-separated, optional): ");
        String tagInput = scanner.nextLine().trim();
        List<String> tags = new ArrayList<>();
        if (!tagInput.isEmpty()) {
            String[] tagArray = tagInput.split(",");
            for (String tag : tagArray) {
                tags.add(tag.trim().toLowerCase());
            }
        }
        
        return new JournalEntry(title, content.toString().trim(), tags);
    }
    
    private String getRandomPrompt() {
        Random random = new Random();
        return JOURNAL_PROMPTS.get(random.nextInt(JOURNAL_PROMPTS.size()));
    }
}

// Suggestion Engine
class SuggestionEngine {
    private static final Map<MoodType, List<String>> MOOD_BASED_SUGGESTIONS = new HashMap<>();
    
    static {
        MOOD_BASED_SUGGESTIONS.put(MoodType.ANXIOUS, Arrays.asList(
            "🧘 Try the 4-7-8 breathing technique: Inhale for 4, hold for 7, exhale for 8",
            "📱 Consider a 5-minute guided meditation",
            "🚶 Take a short walk outside if possible",
            "📝 Write down what's making you feel anxious"
        ));
        
        MOOD_BASED_SUGGESTIONS.put(MoodType.SAD, Arrays.asList(
            "🤗 Reach out to a friend or family member",
            "☀️ Spend a few minutes in natural light",
            "🎵 Listen to music that comforts you",
            "💡 Do one small thing that usually brings you joy"
        ));
        
        MOOD_BASED_SUGGESTIONS.put(MoodType.STRESSED, Arrays.asList(
            "⏰ Try the Pomodoro technique for your tasks",
            "🛁 Take a warm shower or bath",
            "🧘 Practice progressive muscle relaxation",
            "📋 Make a priority list to organize your thoughts"
        ));
        
        MOOD_BASED_SUGGESTIONS.put(MoodType.HAPPY, Arrays.asList(
            "📓 Write about what made you happy today",
            "💌 Share your good mood with someone you care about",
            "🎯 Use this energy to work on a personal goal",
            "🙏 Take a moment to appreciate this feeling"
        ));
        
        MOOD_BASED_SUGGESTIONS.put(MoodType.NEUTRAL, Arrays.asList(
            "🎨 Try a creative activity to spark some energy",
            "📚 Read something interesting or inspiring",
            "🏃 Do some light exercise to boost your mood",
            "🧠 Practice mindfulness and present-moment awareness"
        ));
    }
    
    public List<String> generateSuggestions(User user, DataManager dataManager) {
        List<String> suggestions = new ArrayList<>();
        List<MoodLog> recentLogs = dataManager.getRecentMoodLogs(user.getUserId(), 7);
        
        if (recentLogs.isEmpty()) {
            suggestions.addAll(getGeneralWellnessSuggestions());
            return suggestions;
        }
        
        // Analyze recent mood patterns
        MoodLog latestLog = recentLogs.get(0);
        suggestions.addAll(MOOD_BASED_SUGGESTIONS.getOrDefault(
            latestLog.getMood(), getGeneralWellnessSuggestions()));
        
        // Add trend-based suggestions
        if (detectMoodDecline(recentLogs)) {
            suggestions.add("📈 I notice your mood has been lower recently. Consider scheduling time for self-care");
            suggestions.add("💬 It might help to talk to someone you trust about how you're feeling");
        }
        
        // Add consistency encouragement
        long journalEntries = dataManager.getJournalEntries(user.getUserId()).size();
        if (journalEntries < recentLogs.size()) {
            suggestions.add("✍️ Consider writing in your journal - it can help process your emotions");
        }
        
        return suggestions.subList(0, Math.min(4, suggestions.size())); // Limit to 4 suggestions
    }
    
    private boolean detectMoodDecline(List<MoodLog> recentLogs) {
        if (recentLogs.size() < 3) return false;
        
        double recent = recentLogs.subList(0, 2).stream()
            .mapToInt(log -> log.getMood().getValue())
            .average().orElse(0);
        
        double older = recentLogs.subList(2, Math.min(5, recentLogs.size())).stream()
            .mapToInt(log -> log.getMood().getValue())
            .average().orElse(0);
        
        return recent < older - 0.5; // Decline of more than 0.5 points
    }
    
    private List<String> getGeneralWellnessSuggestions() {
        return Arrays.asList(
            "🌅 Start your day with a few minutes of mindfulness",
            "💧 Remember to stay hydrated throughout the day",
            "📝 Consider keeping a gratitude journal",
            "🚶 Take regular breaks to move your body"
        );
    }
}

// Dashboard Service
class DashboardService {
    private DataManager dataManager;
    
    public DashboardService(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public void displayDashboard(User user) {
        System.out.println("\n=== Your Wellness Dashboard ===");
        System.out.println("Welcome back, " + user.getName() + "!");
        
        List<MoodLog> allMoodLogs = dataManager.getMoodLogs(user.getUserId());
        List<JournalEntry> allJournalEntries = dataManager.getJournalEntries(user.getUserId());
        
        displayMoodSummary(allMoodLogs);
        displayStreaks(allMoodLogs, allJournalEntries);
        displayRecentTrends(dataManager.getRecentMoodLogs(user.getUserId(), 7));
        displayEmotionInsights(allMoodLogs);
    }
    
    private void displayMoodSummary(List<MoodLog> moodLogs) {
        System.out.println("\n📊 Mood Summary:");
        if (moodLogs.isEmpty()) {
            System.out.println("   No mood data yet. Start by logging your first mood!");
            return;
        }
        
        System.out.println("   Total check-ins: " + moodLogs.size());
        
        double averageMood = moodLogs.stream()
            .mapToInt(log -> log.getMood().getValue())
            .average().orElse(0);
        
        System.out.printf("   Average mood: %.1f/5 %s%n", 
            averageMood, getMoodIndicator(averageMood));
        
        // Most common mood
        Map<MoodType, Long> moodFrequency = new HashMap<>();
        for (MoodLog log : moodLogs) {
            moodFrequency.merge(log.getMood(), 1L, Long::sum);
        }
        
        MoodType mostCommon = moodFrequency.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        
        if (mostCommon != null) {
            System.out.println("   Most common mood: " + mostCommon.getEmoji() + " " + mostCommon.getDisplayName());
        }
    }
    
    private void displayStreaks(List<MoodLog> moodLogs, List<JournalEntry> journalEntries) {
        System.out.println("\n🔥 Your Streaks:");
        System.out.println("   Mood check-ins: " + calculateStreak(moodLogs) + " days");
        System.out.println("   Journal entries: " + journalEntries.size() + " total");
    }
    
    private void displayRecentTrends(List<MoodLog> recentLogs) {
        System.out.println("\n📈 Recent Trends (Last 7 days):");
        if (recentLogs.size() < 2) {
            System.out.println("   Not enough data for trend analysis");
            return;
        }
        
        double recentAvg = recentLogs.subList(0, Math.min(3, recentLogs.size())).stream()
            .mapToInt(log -> log.getMood().getValue())
            .average().orElse(0);
        
        double olderAvg = recentLogs.subList(Math.min(3, recentLogs.size()), recentLogs.size()).stream()
            .mapToInt(log -> log.getMood().getValue())
            .average().orElse(0);
        
        if (recentAvg > olderAvg + 0.3) {
            System.out.println("   📈 Your mood has been improving recently!");
        } else if (recentAvg < olderAvg - 0.3) {
            System.out.println("   📉 Your mood has been declining. Consider self-care activities.");
        } else {
            System.out.println("   😊 Your mood has been relatively stable.");
        }
    }
    
    private void displayEmotionInsights(List<MoodLog> moodLogs) {
        System.out.println("\n🏷️  Emotion Insights:");
        
        Map<String, Integer> emotionFrequency = new HashMap<>();
        for (MoodLog log : moodLogs) {
            for (String emotion : log.getEmotionTags()) {
                emotionFrequency.merge(emotion, 1, Integer::sum);
            }
        }
        
        if (emotionFrequency.isEmpty()) {
            System.out.println("   Start adding emotion tags to see insights here!");
            return;
        }
        
        System.out.println("   Most tagged emotions:");
        emotionFrequency.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(3)
            .forEach(entry -> System.out.println("   • " + entry.getKey() + " (" + entry.getValue() + " times)"));
    }
    
    private String getMoodIndicator(double averageMood) {
        if (averageMood >= 4.5) return "🌟";
        if (averageMood >= 3.5) return "😊";
        if (averageMood >= 2.5) return "😐";
        return "💙";
    }
    
    private int calculateStreak(List<MoodLog> moodLogs) {
        if (moodLogs.isEmpty()) return 0;
        
        // Simplified streak calculation - in reality you'd check consecutive days
        return Math.min(moodLogs.size(), 30); // Cap at 30 for display purposes
    }
}