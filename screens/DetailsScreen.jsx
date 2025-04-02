import { View, Text, Button } from 'react-native';
import { Calendar } from 'react-native-calendars';

export default function DetailsScreen({ navigation }) {
    return (
        <View className="flex-1 items-center justify-center bg-white p-4">
            <Text className="text-lg font-bold mb-4">Selecciona una fecha</Text>
            <Calendar
                onDayPress={(day) => alert(`Fecha seleccionada: ${day.dateString}`)}
                markedDates={{
                    '2025-04-10': { selected: true, marked: true, selectedColor: 'blue' },
                }}
            />
            <Button title="Volver a Home" onPress={() => navigation.goBack()} />
        </View>
    );
}
