package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.R
import com.example.quizapp.database.QuizResultDao
import com.example.quizapp.models.Category
import com.example.quizapp.models.Question
import com.example.quizapp.models.QuizResult

class QuizRepository(private val context: Context) {

    private val quizResultDao = QuizResultDao(context)

    fun getCategories(): List<Category> {
        return listOf(
            Category("science", "Science", R.drawable.ic_science, 10),
            Category("history", "History", R.drawable.ic_history_category, 10),
            Category("geography", "Geography", R.drawable.ic_geography, 10),
            Category("literature", "Literature", R.drawable.ic_literature, 10),
            Category("technology", "Technology", R.drawable.ic_technology, 10)
        )
    }

    fun getQuestionsByCategory(categoryId: String): List<Question> {
        return when (categoryId) {
            "science" -> getScienceQuestions()
            "history" -> getHistoryQuestions()
            "geography" -> getGeographyQuestions()
            "literature" -> getLiteratureQuestions()
            "technology" -> getTechnologyQuestions()
            else -> emptyList()
        }
    }

    fun saveQuizResult(result: QuizResult): Long {
        return quizResultDao.insertQuizResult(result)
    }

    fun getQuizResults(): List<QuizResult> {
        return quizResultDao.getAllQuizResults()
    }


    private fun getScienceQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "What is the chemical symbol for gold?",
                options = listOf("Go", "Gd", "Au", "Ag"),
                correctAnswerIndex = 2,
                category = "science",
                explanation = "Au (from Latin: aurum) is the chemical symbol for gold."
            ),
            Question(
                id = 2,
                text = "Which planet is known as the Red Planet?",
                options = listOf("Venus", "Jupiter", "Mars", "Saturn"),
                correctAnswerIndex = 2,
                category = "science",
                explanation = "Mars is called the Red Planet due to the iron oxide (rust) on its surface."
            ),
            Question(
                id = 3,
                text = "What is the largest organ in the human body?",
                options = listOf("Liver", "Brain", "Heart", "Skin"),
                correctAnswerIndex = 3,
                category = "science",
                explanation = "The skin is the largest organ, covering about 20 square feet in adults."
            ),
            Question(
                id = 4,
                text = "Which of these is NOT a state of matter?",
                options = listOf("Solid", "Liquid", "Gas", "Molecule"),
                correctAnswerIndex = 3,
                category = "science",
                explanation = "Molecule is a particle, not a state of matter. The main states are solid, liquid, gas, and plasma."
            ),
            Question(
                id = 5,
                text = "What is the hardest natural substance on Earth?",
                options = listOf("Diamond", "Titanium", "Platinum", "Graphene"),
                correctAnswerIndex = 0,
                category = "science",
                explanation = "Diamond is the hardest known natural material, scoring 10 on the Mohs scale."
            ),
            Question(
                id = 6,
                text = "Which element has the atomic number 1?",
                options = listOf("Oxygen", "Carbon", "Hydrogen", "Helium"),
                correctAnswerIndex = 2,
                category = "science",
                explanation = "Hydrogen has the atomic number 1, making it the lightest element."
            ),
            Question(
                id = 7,
                text = "Which of these animals is NOT a mammal?",
                options = listOf("Dolphin", "Bat", "Whale", "Penguin"),
                correctAnswerIndex = 3,
                category = "science",
                explanation = "Penguins are birds, not mammals. They lay eggs and have feathers."
            ),
            Question(
                id = 8,
                text = "What is the speed of light in a vacuum?",
                options = listOf("300,000 km/s", "150,000 km/s", "1,000,000 km/s", "100,000 km/s"),
                correctAnswerIndex = 0,
                category = "science",
                explanation = "The speed of light in a vacuum is approximately 300,000 kilometers per second."
            ),
            Question(
                id = 9,
                text = "What is the powerhouse of the cell?",
                options = listOf("Nucleus", "Mitochondria", "Endoplasmic Reticulum", "Golgi Apparatus"),
                correctAnswerIndex = 1,
                category = "science",
                explanation = "Mitochondria are known as the powerhouse of the cell as they generate most of the cell's energy."
            ),
            Question(
                id = 10,
                text = "Which scientist proposed the theory of relativity?",
                options = listOf("Isaac Newton", "Albert Einstein", "Nikola Tesla", "Marie Curie"),
                correctAnswerIndex = 1,
                category = "science",
                explanation = "Albert Einstein proposed the theory of relativity, revolutionizing our understanding of space and time."
            )
        )
    }

    private fun getHistoryQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "In which year did World War II end?",
                options = listOf("1943", "1944", "1945", "1946"),
                correctAnswerIndex = 2,
                category = "history",
                explanation = "World War II ended in 1945 with the surrender of Japan after the atomic bombings."
            ),
            Question(
                id = 2,
                text = "Who was the first President of the United States?",
                options = listOf("Thomas Jefferson", "John Adams", "George Washington", "Benjamin Franklin"),
                correctAnswerIndex = 2,
                category = "history",
                explanation = "George Washington served as the first President from 1789 to 1797."
            ),
            Question(
                id = 3,
                text = "The ancient city of Rome was founded in which year?",
                options = listOf("753 BCE", "500 BCE", "1000 BCE", "250 BCE"),
                correctAnswerIndex = 0,
                category = "history",
                explanation = "According to tradition, Rome was founded by Romulus and Remus in 753 BCE."
            ),
            Question(
                id = 4,
                text = "Which empire was ruled by Genghis Khan?",
                options = listOf("Ottoman Empire", "Mongol Empire", "Roman Empire", "Byzantine Empire"),
                correctAnswerIndex = 1,
                category = "history",
                explanation = "Genghis Khan founded and ruled the Mongol Empire, the largest contiguous land empire in history."
            ),
            Question(
                id = 5,
                text = "The French Revolution began in which year?",
                options = listOf("1789", "1776", "1804", "1815"),
                correctAnswerIndex = 0,
                category = "history",
                explanation = "The French Revolution began in 1789 with the storming of the Bastille."
            ),
            Question(
                id = 6,
                text = "Who was the first woman to win a Nobel Prize?",
                options = listOf("Mother Teresa", "Marie Curie", "Rosalind Franklin", "Jane Goodall"),
                correctAnswerIndex = 1,
                category = "history",
                explanation = "Marie Curie was the first woman to win a Nobel Prize, winning the Physics Prize in 1903."
            ),
            Question(
                id = 7,
                text = "The Great Wall of China was built primarily during which dynasty?",
                options = listOf("Han Dynasty", "Tang Dynasty", "Ming Dynasty", "Qing Dynasty"),
                correctAnswerIndex = 2,
                category = "history",
                explanation = "While parts of the wall existed earlier, most of the current Great Wall was built during the Ming Dynasty."
            ),
            Question(
                id = 8,
                text = "Which of these countries was NOT part of the Allied Powers in World War II?",
                options = listOf("United States", "Soviet Union", "Italy", "United Kingdom"),
                correctAnswerIndex = 2,
                category = "history",
                explanation = "Italy was part of the Axis Powers alongside Nazi Germany and Japan."
            ),
            Question(
                id = 9,
                text = "Who wrote the 'I Have a Dream' speech?",
                options = listOf("Malcolm X", "Martin Luther King Jr.", "Rosa Parks", "Barack Obama"),
                correctAnswerIndex = 1,
                category = "history",
                explanation = "The 'I Have a Dream' speech was delivered by Martin Luther King Jr. in 1963."
            ),
            Question(
                id = 10,
                text = "In which year did the Berlin Wall fall?",
                options = listOf("1987", "1989", "1991", "1993"),
                correctAnswerIndex = 1,
                category = "history",
                explanation = "The Berlin Wall fell on November 9, 1989, marking the beginning of the end of the Cold War."
            )
        )
    }

    private fun getGeographyQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "Which is the largest ocean on Earth?",
                options = listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"),
                correctAnswerIndex = 3,
                category = "geography",
                explanation = "The Pacific Ocean is the largest and deepest ocean on Earth."
            ),
            Question(
                id = 2,
                text = "What is the capital of Australia?",
                options = listOf("Sydney", "Melbourne", "Canberra", "Perth"),
                correctAnswerIndex = 2,
                category = "geography",
                explanation = "Canberra is the capital city of Australia, not Sydney or Melbourne."
            ),
            Question(
                id = 3,
                text = "Which mountain range separates Europe and Asia?",
                options = listOf("Alps", "Andes", "Himalayas", "Urals"),
                correctAnswerIndex = 3,
                category = "geography",
                explanation = "The Ural Mountains form a natural boundary between Europe and Asia."
            ),
            Question(
                id = 4,
                text = "Which of these countries does NOT border France?",
                options = listOf("Spain", "Italy", "Portugal", "Belgium"),
                correctAnswerIndex = 2,
                category = "geography",
                explanation = "Portugal does not share a border with France. It borders only Spain."
            ),
            Question(
                id = 5,
                text = "The Great Barrier Reef is located off the coast of which country?",
                options = listOf("Brazil", "Australia", "Indonesia", "Mexico"),
                correctAnswerIndex = 1,
                category = "geography",
                explanation = "The Great Barrier Reef is located in the Coral Sea, off the coast of Queensland, Australia."
            ),
            Question(
                id = 6,
                text = "Which is the longest river in the world?",
                options = listOf("Amazon", "Nile", "Yangtze", "Mississippi"),
                correctAnswerIndex = 1,
                category = "geography",
                explanation = "The Nile River is considered the longest river at approximately 6,650 kilometers."
            ),
            Question(
                id = 7,
                text = "What is the smallest country in the world by land area?",
                options = listOf("Monaco", "Maldives", "Vatican City", "San Marino"),
                correctAnswerIndex = 2,
                category = "geography",
                explanation = "Vatican City is the smallest country in the world with an area of just 0.49 square kilometers."
            ),
            Question(
                id = 8,
                text = "Which desert is the largest in the world?",
                options = listOf("Gobi", "Kalahari", "Sahara", "Antarctic"),
                correctAnswerIndex = 3,
                category = "geography",
                explanation = "The Antarctic Desert is the largest desert in the world, covering 14 million square kilometers."
            ),
            Question(
                id = 9,
                text = "Which continent is the least populated?",
                options = listOf("Australia", "Europe", "Antarctica", "South America"),
                correctAnswerIndex = 2,
                category = "geography",
                explanation = "Antarctica has no permanent population, making it the least populated continent."
            ),
            Question(
                id = 10,
                text = "Which of these cities is NOT in Japan?",
                options = listOf("Osaka", "Kyoto", "Busan", "Tokyo"),
                correctAnswerIndex = 2,
                category = "geography",
                explanation = "Busan is a city in South Korea, not Japan."
            )
        )
    }

    private fun getLiteratureQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "Who wrote 'Pride and Prejudice'?",
                options = listOf("Charlotte Brontë", "Jane Austen", "Virginia Woolf", "Emily Brontë"),
                correctAnswerIndex = 1,
                category = "literature",
                explanation = "Jane Austen published 'Pride and Prejudice' in 1813."
            ),
            Question(
                id = 2,
                text = "Which character says 'To be or not to be, that is the question'?",
                options = listOf("Macbeth", "Romeo", "Hamlet", "Othello"),
                correctAnswerIndex = 2,
                category = "literature",
                explanation = "This famous soliloquy is spoken by Hamlet in Shakespeare's play 'Hamlet'."
            ),
            Question(
                id = 3,
                text = "Who wrote 'War and Peace'?",
                options = listOf("Fyodor Dostoevsky", "Anton Chekhov", "Leo Tolstoy", "Ivan Turgenev"),
                correctAnswerIndex = 2,
                category = "literature",
                explanation = "Leo Tolstoy wrote 'War and Peace', published in 1869."
            ),
            Question(
                id = 4,
                text = "In which fictional place does 'The Lord of the Rings' primarily take place?",
                options = listOf("Narnia", "Middle-earth", "Westeros", "Hogwarts"),
                correctAnswerIndex = 1,
                category = "literature",
                explanation = "J.R.R. Tolkien's 'The Lord of the Rings' is set in the fictional world of Middle-earth."
            ),
            Question(
                id = 5,
                text = "Who wrote 'One Hundred Years of Solitude'?",
                options = listOf("Pablo Neruda", "Gabriel García Márquez", "Isabel Allende", "Jorge Luis Borges"),
                correctAnswerIndex = 1,
                category = "literature",
                explanation = "Gabriel García Márquez wrote this landmark of magical realism, published in 1967."
            ),
            Question(
                id = 6,
                text = "Which of these books was NOT written by Charles Dickens?",
                options = listOf("Oliver Twist", "Great Expectations", "Wuthering Heights", "David Copperfield"),
                correctAnswerIndex = 2,
                category = "literature",
                explanation = "Wuthering Heights was written by Emily Brontë, not Charles Dickens."
            ),
            Question(
                id = 7,
                text = "Who is the author of 'The Great Gatsby'?",
                options = listOf("F. Scott Fitzgerald", "Ernest Hemingway", "William Faulkner", "John Steinbeck"),
                correctAnswerIndex = 0,
                category = "literature",
                explanation = "F. Scott Fitzgerald wrote 'The Great Gatsby', published in 1925."
            ),
            Question(
                id = 8,
                text = "Which Shakespearean play features the character Puck?",
                options = listOf("The Tempest", "A Midsummer Night's Dream", "Twelfth Night", "As You Like It"),
                correctAnswerIndex = 1,
                category = "literature",
                explanation = "Puck is a mischievous fairy in 'A Midsummer Night's Dream'."
            ),
            Question(
                id = 9,
                text = "Who wrote 'The Catcher in the Rye'?",
                options = listOf("Jack Kerouac", "J.D. Salinger", "Kurt Vonnegut", "Joseph Heller"),
                correctAnswerIndex = 1,
                category = "literature",
                explanation = "J.D. Salinger wrote 'The Catcher in the Rye', published in 1951."
            ),
            Question(
                id = 10,
                text = "Which novel begins with the line 'It was the best of times, it was the worst of times'?",
                options = listOf("Oliver Twist", "Great Expectations", "A Tale of Two Cities", "Bleak House"),
                correctAnswerIndex = 2,
                category = "literature",
                explanation = "This famous opening line is from Charles Dickens' 'A Tale of Two Cities'."
            )
        )
    }

    private fun getTechnologyQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "Who is considered the founder of Microsoft?",
                options = listOf("Steve Jobs", "Bill Gates", "Mark Zuckerberg", "Jeff Bezos"),
                correctAnswerIndex = 1,
                category = "technology",
                explanation = "Bill Gates co-founded Microsoft with Paul Allen in 1975."
            ),
            Question(
                id = 2,
                text = "What does CPU stand for?",
                options = listOf("Central Processing Unit", "Computer Personal Unit", "Central Processor Utility", "Central Program Unit"),
                correctAnswerIndex = 0,
                category = "technology",
                explanation = "CPU stands for Central Processing Unit, the primary component of a computer that performs most calculations."
            ),
            Question(
                id = 3,
                text = "What programming language was developed by James Gosling at Sun Microsystems?",
                options = listOf("Python", "Java", "C++", "Ruby"),
                correctAnswerIndex = 1,
                category = "technology",
                explanation = "Java was developed by James Gosling at Sun Microsystems and released in 1995."
            ),
            Question(
                id = 4,
                text = "What does HTML stand for?",
                options = listOf("Hyper Text Markup Language", "High Tech Modern Language", "Hyperlink and Text Markup Language", "Home Tool Markup Language"),
                correctAnswerIndex = 0,
                category = "technology",
                explanation = "HTML stands for Hyper Text Markup Language, the standard language for creating web pages."
            ),
            Question(
                id = 5,
                text = "Which of these is NOT a programming language?",
                options = listOf("Python", "Java", "HTML", "Leopard"),
                correctAnswerIndex = 3,
                category = "technology",
                explanation = "Leopard is not a programming language. It was a version of Apple's macOS."
            ),
            Question(
                id = 6,
                text = "In which decade was the first smartphone released?",
                options = listOf("1980s", "1990s", "2000s", "2010s"),
                correctAnswerIndex = 1,
                category = "technology",
                explanation = "The first smartphone, the IBM Simon, was released in 1994."
            ),
            Question(
                id = 7,
                text = "What does 'GPU' stand for?",
                options = listOf("General Processing Unit", "Graphics Processing Unit", "Graphical Picture Unit", "General Power Unit"),
                correctAnswerIndex = 1,
                category = "technology",
                explanation = "GPU stands for Graphics Processing Unit, specialized for rendering images, videos, and animations."
            ),
            Question(
                id = 8,
                text = "Which company developed the Android operating system?",
                options = listOf("Apple", "Microsoft", "Google", "Samsung"),
                correctAnswerIndex = 2,
                category = "technology",
                explanation = "Android was developed by Android Inc., which Google bought in 2005."
            ),
            Question(
                id = 9,
                text = "What does 'IoT' stand for in technology?",
                options = listOf("Internet of Things", "Input of Time", "Intelligence of Technology", "Input/Output Technology"),
                correctAnswerIndex = 0,
                category = "technology",
                explanation = "IoT stands for Internet of Things, referring to physical objects with sensors, software, and connectivity."
            ),
            Question(
                id = 10,
                text = "Which of these is a version control system?",
                options = listOf("Docker", "Kubernetes", "Git", "TensorFlow"),
                correctAnswerIndex = 2,
                category = "technology",
                explanation = "Git is a distributed version control system for tracking changes in source code during software development."
            )
        )
    }
}