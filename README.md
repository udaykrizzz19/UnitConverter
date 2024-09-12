_**Unit Converter App**_

This Unit Converter App, built using Jetpack Compose, allows users to easily convert between different units of measurement, including distance, temperature, and weight. The app features a clean and intuitive UI, with dropdown menus to select input and output units, and instant conversion as the user enters values.

_**Features:**_ Category-specific conversions: Converts units only within the same category (distance, temperature, or weight), ensuring accurate and meaningful results. Distance conversions: Convert between units such as meters, centimeters, millimeters, and feet. Temperature conversions: Convert between Celsius, Fahrenheit, and Kelvin. Weight conversions: Convert between kilograms, grams, and pounds. Supports Dark and White Modes in the mobile

_**Error handling: **_ If units from different categories are selected, the app displays an "Invalid Conversion" message, ensuring user clarity and preventing incorrect conversions. Responsive and User-Friendly: Real-time conversion as values are entered, with a seamless, easy-to-navigate interface.

_**Technologies Used:**_ 
1.Jetpack Compose: Modern UI Toolkit: Jetpack Compose is used to create the entire UI of the app. It provides a declarative approach to building UI components, making the code more readable and efficient. Composable Functions: These reusable functions allow the UI to update dynamically in response to state changes, making the app highly responsive to user interactions.
2.Kotlin: Concise and Expressive: Kotlin is used for all logic and functionality in the app, providing a concise and expressive syntax that reduces boilerplate code. Type-Safe Conversions: Kotlin’s strong type system ensures safe conversions, with functions that handle nullable types and provide fallback logic.
3.State Management with Jetpack Compose: remember and mutableStateOf: Used to handle state in the app, ensuring that the UI updates seamlessly as users change values and select different units. State Preservation: The app retains the current state of input and output values, even when the UI is re-rendered, providing a smooth user experience.

_**How it Works:**_ The user selects the input and output units from dropdown menus. The app automatically checks if the units are within the same category (e.g., distance units can only be converted to other distance units). The converted value is displayed immediately after the user enters an input value. If the units selected are from different categories (e.g., temperature to weight), the app will return "Invalid Conversion."
