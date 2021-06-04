function isNullOrEmpty(value) {
  if (typeof value === 'string') {
    return value === ''
  } else if (typeof value === 'undefined') {
    return true
  } else if (value instanceof Array) {
    return value === null || value.length === 0
  } else if (value instanceof Object) {
    return value === null || Object.keys(value).length === 0
  } else {
    return value === null
  }
}

export { isNullOrEmpty };
