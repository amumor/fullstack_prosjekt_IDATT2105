// Format LocalDateTime to a readable format
export function format(dateTimeString, listing) {
    if (!dateTimeString) {
      dateTimeString = listing;
    } 
    if(!dateTimeString) return 'N/A';
    const date = new Date(dateTimeString); 
    if (isNaN(date)) {
      console.error('Invalid date format:', dateTimeString);
      return 'Invalid Date';
    }
    return date.toLocaleString();
  };